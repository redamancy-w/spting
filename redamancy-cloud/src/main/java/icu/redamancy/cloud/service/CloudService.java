package icu.redamancy.cloud.service;

import com.alibaba.druid.sql.visitor.functions.Length;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.redamancy.common.feignclient.PictureClient;
import icu.redamancy.common.model.bo.*;
import icu.redamancy.common.model.dao.auth.mapper.UserMapper;
import icu.redamancy.common.model.dao.cloud.*;
import icu.redamancy.common.model.dao.picture.PictureDaoServiceImpl;
import icu.redamancy.common.model.dao.picture.service.PictureService;
import icu.redamancy.common.model.dto.BuyGoodsListDTO;
import icu.redamancy.common.model.dto.DDeclareDTO;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.model.pojo.cloud.*;
import icu.redamancy.common.model.pojo.picture.Picture;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.jjwt.ParsingUserJwtInfo;
import icu.redamancy.common.utils.lock.SimpleRedisLock;
import icu.redamancy.common.utils.result.Result;
import icu.redamancy.common.utils.result.ResultCode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author redamancy
 * @* @Date 2022/6/7 10:15
 * @Version 1.0
 */
@Service
public class CloudService {

    @Value("${spring.application.name}")
    private String objectName;

    @Resource
    private DeclareDaoServiceImpl declareDaoService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PictureClient pictureClient;

    @Resource
    private StringRedisTemplate stringRedisTemplatel;

    @Resource
    private PictureService pictureService;

    @Autowired
    private ObjectMapper objectMapper;


    public boolean addAccount(DDeclareDTO dDeclareDTO){
        int balance =Integer.parseInt( dDeclareDTO.getPopulation()) * 100;

        boolean ok = false;

        if (accountDaoService.lambdaQuery().eq(Account::getHousenumberid,dDeclareDTO.getHousenumberid()).exists()){
             ok = accountDaoService.lambdaUpdate()
                    .eq(Account::getHousenumberid,dDeclareDTO.getHousenumberid())
                    .setSql("consumption = consumption+"+dDeclareDTO.getPopulation())
                    .setSql("balance = balance + "+ balance)
                    .update();
        }else {
            ok = accountDaoService.save(new Account()
                    .setBalance(balance)
                    .setHousenumberid(Long.valueOf(dDeclareDTO.getHousenumberid()))
                    .setConsumption(Integer.valueOf(dDeclareDTO.getPopulation())));
        }
        return ok;
    }

//    提交报表
    public Boolean addDeclare(DDeclareDTO dDeclareDTO, MultipartFile[] file) throws IOException {
        int sex = 0;

        if (dDeclareDTO.getSex().equals("男"))
            sex =1;

        String userId = ParsingUserJwtInfo
                .GetParsingUserJwtInfo()
                .getUserId();

        EntityDeclare entityDeclare = new EntityDeclare()
                .setAddresses(dDeclareDTO.getAddresses())
                .setIdcard(dDeclareDTO.getIdcard())
                .setMobile(dDeclareDTO.getMobile())
                .setName(dDeclareDTO.getName())
                .setRelation(dDeclareDTO.getRelation())
                .setSex(sex)
                .setHousenumberid(Long.valueOf(dDeclareDTO.getHousenumberid()))
                .setUserid(Long.valueOf(userId));

        List<Long> idList = null;
        idList = pictureClient.addPicture(objectName, userId, file);

        String idJson = objectMapper.writeValueAsString(idList);
        entityDeclare.setPictureList(idJson);
        userMapper.update(null, new UpdateWrapper<EntityUser>().eq("id", userId)
                .set("name", entityDeclare.getName())
                .set("state",3)
                .set("housenumberid",dDeclareDTO.getHousenumberid()));

        return declareDaoService.save(entityDeclare);

    }

//    报表首页
    public void init() {
        List<EntityDeclare> declareList;
        List<DeclareBo> declareListBo;

        Page<EntityDeclare> page = declareDaoService.lambdaQuery().orderByDesc(EntityDeclare::getUpdateTime).page(new Page<>(1, 10));
        declareList = page.getRecords();
        declareListBo = declareList.stream().map(v -> {
            DeclareBo declareBo = new DeclareBo();
            BeanUtils.copyProperties(v, declareBo);
            if (!ObjectUtils.isEmpty(v.getPictureList()) && !v.getPictureList().equals("null")) {

                List<Long> id = JSON.parseArray(v.getPictureList(), Long.class);
                List<Picture> pictureList = pictureService.listByIds(id);
                declareBo.setImageList(pictureList);
            }

            return declareBo;
        }).collect(Collectors.toList());

        String jsonList = null;
        try {
            jsonList = objectMapper.writeValueAsString(declareListBo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stringRedisTemplatel.opsForValue().set("cloud:declare:home:init", jsonList);
    }


    /**
     * 分页报表
     * @param current
     * @param size
     * @param updateTime
     * @return
     */
    public List<DeclareBo> getDeclare(Integer current, Integer size, Long updateTime) {

        List<DeclareBo> declareBos;

        Page<EntityDeclare> page = declareDaoService
                .lambdaQuery()
                .orderByDesc(EntityDeclare::getUpdateTime)
                .lt(EntityDeclare::getUpdateTime, updateTime)
                .page(new Page<>(1, size));

        declareBos = page.getRecords().stream().map(v -> {
            DeclareBo declareBo = new DeclareBo();
            BeanUtils.copyProperties(v, declareBo);
            if (!ObjectUtils.isEmpty(v.getPictureList()) && !v.getPictureList().equals("null")) {
                List<Long> id = JSON.parseArray(v.getPictureList(), Long.class);
                List<Picture> pictureList = pictureService.listByIds(id);

                declareBo.setImageList(pictureList);

            }
            return declareBo;

        }).collect(Collectors.toList());
        return declareBos;
    }


    /**
     * 小程序首页
     * @return
     */
    public Map<String,Object> initHome(){
        Map<String,Object> map = new HashMap<>();
        String userId = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();
        UserBO userBO = new UserBO();

        BeanUtils.copyProperties(this.getUser(userId),userBO);

        map.put("userinfo",userBO);
        map.put("houseInfo",this.getHouseNumberBo(userId));

        return map;
    }

    public EntityUser getUser(String userId){

        UserBO userBO = new UserBO();


        return userMapper.selectById(userId);
    }

    @Resource
    private StayDaoServiceImpl stayDaoService;

    @Resource
    private HouseNumberDaoServiceImpl houseNumberDaoService;

    @Resource
    private UnitDaoServiceImpl unitDaoService;

    /**
     * 获得用户的居住门牌号和单元号
     * @param userId
     * @return
     */
    public HouseNumberBo getHouseNumberBo(String userId){

        Stay stay = stayDaoService.lambdaQuery().eq(Stay::getUserId,userId).one();
        HouseNumberBo houseNumberBo = new HouseNumberBo();

        if (stay == null){
            return houseNumberBo;
        }

        HouseNumber houseNumber = houseNumberDaoService.getById(stay.getHousenumberid());
        BeanUtils.copyProperties(houseNumber,houseNumberBo);

        Unit unit = unitDaoService.getById(houseNumber.getUnit());

        houseNumberBo.setUnitNumber(unit.getUnit());

        return houseNumberBo;
    }


    public Long addUnit(Unit unit) {

        boolean exists = unitDaoService.lambdaQuery().eq(Unit::getUnit,unit.getUnit()).exists();
        if (exists){
            return unitDaoService.lambdaQuery().eq(Unit::getUnit,unit.getUnit()).one().getId();
        }
        unitDaoService.save(unit);
        return unit.getId();
    }


    public Boolean addHouseNumber(HouseNumber houseNumber, Unit unit) {

        Long unitID = this.addUnit(unit);
        houseNumber.setUnit(unitID);
        boolean exists = houseNumberDaoService.lambdaQuery().eq(HouseNumber::getHousNumber,houseNumber.getHousNumber()).exists();
        if (exists){
            return true;
        }

        return houseNumberDaoService.save(houseNumber);
    }

    public Boolean addStay(DDeclareDTO dDeclareDTO){

        String id = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();
        Stay stay = new Stay();
        stay.setUserId(Long.valueOf(id)).setHousenumberid(Long.valueOf(dDeclareDTO.getHousenumberid()));

        if (stayDaoService.lambdaQuery().eq(Stay::getUserId,id).exists()){
            return true;
        }

        return stayDaoService.save(stay);
    }

    public List<HouseNumberList> getHouseNumberList(){

        List<Unit> units = unitDaoService.list();
         List<HouseNumberList> houseNumberLists = units.stream().map(v->{
             HouseNumberList houseNumberList = new HouseNumberList();
             BeanUtils.copyProperties(v,houseNumberList);
             List<HouseNumber> unit_houseNumbers = houseNumberDaoService.lambdaQuery().eq(HouseNumber::getUnit,v.getId()).list();

             houseNumberList.setHouseNumbers(unit_houseNumbers);
             return houseNumberList;
        }).collect(Collectors.toList());
         return houseNumberLists;
    }

    @Resource
    private MaterialsDaoServiceImpl materialsDaoService;

    @Resource
    private MaterialsTitleDaoServiceImpl materialsTitleDaoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<MaterialsBO> getMaterialsList(){

        List<MaterialsTitle> materialsTitles_DB = materialsTitleDaoService.list();

        List<MaterialsBO> materialsBOList   = materialsTitles_DB.stream().map(v->{
            MaterialsBO materialsBO = new MaterialsBO();
            BeanUtils.copyProperties(v,materialsBO);

            Set<String> keys = redisTemplate.keys("goodlistT:"+v.getId()+":*");

            List<Materials> materialsList_DB = new ArrayList<>();
            assert keys != null;
            for (String key : keys){
                Materials materials = (Materials) redisTemplate.opsForValue().get(key);
                materialsList_DB.add(materials);
            }
            List<MaterialsImageBO> materialsImageBO  = materialsList_DB
//                    .lambdaQuery()
//                    .eq(Materials::getTitleId,v.getId())
//                    .list()
                    .stream()
                    .map(val->{
                     MaterialsImageBO  materialsImageBO_item =   new MaterialsImageBO();
                     BeanUtils.copyProperties(val,materialsImageBO_item);
                        if (!ObjectUtils.isEmpty(val.getImageList()) && !val.getImageList().equals("null")) {
                            List<Long> id = JSON.parseArray(val.getImageList(), Long.class);
                            List<Picture> pictureList = pictureService.listByIds(id);
                            Iterator<Picture> iterable = pictureList.iterator();
                            materialsImageBO_item.setFilePath(iterable.next().getFilepath());
                        }
                        return materialsImageBO_item;
            }).collect(Collectors.toList());

            materialsBO.setMaterialsBO(materialsImageBO);

            return materialsBO;
        }).collect(Collectors.toList());
        return materialsBOList;
    }

    public void intiGoodsList(){
        List<MaterialsTitle> materialsTitles_DB = materialsTitleDaoService.list();
        for (MaterialsTitle item :
                materialsTitles_DB) {
            for (Materials i : materialsDaoService.lambdaQuery().eq(Materials::getTitleId,item.getId()).list()){
                redisTemplate.opsForValue().set("goodlist:"+i.getId(),i);
            }
        }
    }

    public void intiGoodsListHome(){
        List<MaterialsTitle> materialsTitles_DB = materialsTitleDaoService.list();
        for (MaterialsTitle item :
                materialsTitles_DB) {
            for (Materials i : materialsDaoService.lambdaQuery().eq(Materials::getTitleId,item.getId()).list()){
                redisTemplate.opsForValue().set("goodlistT:"+item.getId()+":"+i.getId(),i);
            }
        }
    }

    @Resource
    private AccountDaoServiceImpl accountDaoService;

    public Integer getUserAccount(){
        String id = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();
        EntityUser user = getUser(id);

        Account account = accountDaoService.lambdaQuery().eq(Account::getHousenumberid,user.getHousenumber()).one();

        return account.getBalance();
    }

    @Resource
    private SimpleRedisLock simpleRedisLock;


    @Resource
    private OrderDaoServiceImpl orderDaoService;

    @Resource
    private OrderMaterialsDaoServiceImpl orderMaterialsDaoService;




    @Transactional
    public Boolean BuyGoods(List<BuyGoodsListDTO> goodsListDTOS) throws InterruptedException {

        int allGoods_Price = 0;
        EntityUser user = getUser(ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId());

        List<OrderMaterials> orderMaterials_DB = new ArrayList<>();

        for (BuyGoodsListDTO goodsListDTO : goodsListDTOS) {
            allGoods_Price = allGoods_Price +Integer.parseInt(goodsListDTO.getPrice()) * Integer.parseInt(goodsListDTO.getBuyNum());
        }

        if (!simpleRedisLock.tryLock(user.getHousenumber().toString(),1000L)){
            throw new BaseException(ResultCode.FEEDBACK);
        }

        Account account = (Account) redisTemplate.opsForValue().get("account:"+user.getHousenumber());

        assert account != null;
        if (account.getBalance() < allGoods_Price){
            throw new BaseException(ResultCode.FEEDBACK.code(),"余额不足");
        }

        List<Long>  materialIdList= buckleDB(goodsListDTOS,user);

        String jsonList = "";

        try {
            jsonList = objectMapper.writeValueAsString(materialIdList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Order order = new Order()
                .setHousenumberid(user.getHousenumber())
                .setMaterialsListId(jsonList)
                .setUserid(user.getId());

        orderDaoService.save(order);

        accountDaoService.lambdaUpdate()
                .set(Account::getBalance,account.getBalance()-allGoods_Price)
                .eq(Account::getId,account.getId())
                .update();

        simpleRedisLock.unlock(user.getHousenumber().toString());

        return true;

    }

    @Transactional
    public List<Long> buckleDB(List<BuyGoodsListDTO> buyGoodsListDTOS,EntityUser user){

        List<Long> oMId = new ArrayList<>();

        for (BuyGoodsListDTO buyGoodsListDTO : buyGoodsListDTOS) {
            if (!simpleRedisLock.tryLock(buyGoodsListDTO.getId(),10000L))
                throw new BaseException(ResultCode.FEEDBACK.code(),"系统繁忙");

            Materials materials = (Materials) redisTemplate.opsForValue().get("goodlist:"+buyGoodsListDTO.getId());
            assert materials != null;

            if (!((materials.getNumber() - materials.getSoldNumber()) >= Integer.parseInt(buyGoodsListDTO.getBuyNum()))){
                throw new BaseException(ResultCode.FEEDBACK.code(),"对不起，所选商品库存不足");
            }

            materials = materials.setSoldNumber(materials.getSoldNumber() +Integer.parseInt( buyGoodsListDTO.getBuyNum()));

//            materialsDaoService.lambdaUpdate().setSql().eq().update()

            boolean ok = materialsDaoService.lambdaUpdate().eq(Materials::getId,materials.getId()).update(materials);
            if (!ok)
                throw new BaseException(ResultCode.FEEDBACK);

            OrderMaterials orderMaterials = new OrderMaterials()
                    .setAmount(Integer.valueOf(buyGoodsListDTO.getBuyNum()))
                    .setMaterialsid(Long.valueOf(buyGoodsListDTO.getId()))
                    .setHousenumberid(user.getHousenumber());

            Boolean createOrder = orderMaterialsDaoService.save(orderMaterials);

            if (!createOrder)
                throw new BaseException(ResultCode.FEEDBACK);

            oMId.add(orderMaterials.getId());

            simpleRedisLock.unlock(buyGoodsListDTO.getId());

        }

        return oMId;

    }

    public void initAccount(){
        List<Account>  accountList_DB =  accountDaoService.list();
        for (Account account : accountList_DB) {
            redisTemplate.opsForValue().set( "account:"+account.getHousenumberid(),account);
        }
    }

    public List<OrderBO> getOrder(){

        String id = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();

        List<Order> orderList = orderDaoService.lambdaQuery().eq(Order::getUserid,id).list();

        return  cycleOrderMaterialsBo(orderList);
    }
    public List<OrderBO> getOrder_home(){

        String id = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();

        EntityUser user = getUser(id);

        List<Order> orderList = orderDaoService.lambdaQuery().eq(Order::getHousenumberid,user.getHousenumber()).list();

        return  cycleOrderMaterialsBo(orderList);
    }

    @Resource
    private PictureDaoServiceImpl pictureDaoService;

    public List<OrderBO> cycleOrderMaterialsBo(List<Order> orderList_DB){

        List<OrderBO> orderBOList = orderList_DB.stream().map(v->{

            UserBO userBO = new UserBO();

            HouseNumber houseNumber = houseNumberDaoService.lambdaQuery().eq(HouseNumber::getId,v.getHousenumberid()).one();
            userBO.setHousenumber(Long.valueOf(houseNumber.getHousNumber()));
            Unit unit = unitDaoService.lambdaQuery().eq(Unit::getId,houseNumber.getUnit()).one();
            userBO.setUnit(unit.getUnit());

            EntityUser user = getUser(String.valueOf(v.getUserid()));

            userBO.setName(user.getName());
            userBO.setAvatarUrl(user.getAvatarUrl());

            OrderBO orderBO = new OrderBO();
            orderBO.setUserBO(userBO);

            BeanUtils.copyProperties(v,orderBO);
            List<Long> id = JSON.parseArray(v.getMaterialsListId(),Long.class);

            assert id.size() == 0 : "id为空";
             List<OrderMaterialsBo> orderMaterials = orderMaterialsDaoService.listByIds(id)
                     .stream()
                     .map(var ->{

                         Materials  materials = materialsDaoService.lambdaQuery()
                                 .eq(Materials::getId,var.getMaterialsid())
                                 .one();

                         List<Long> imageList = JSON.parseArray(materials.getImageList(),Long.class);
                         assert imageList.size() == 0 : "id为空";

                         String filePath = pictureDaoService
                                 .listByIds(imageList)
                                 .get(0)
                                 .getFilepath();
                        OrderMaterialsBo orderMaterialsBo = new OrderMaterialsBo();
                        BeanUtils.copyProperties(var,orderMaterialsBo);

                        return orderMaterialsBo.setFilePath(filePath).setMaterials(materials);
                     }).collect(Collectors.toList());

             orderBO.setMaterialsBOList(orderMaterials);

            return orderBO;
        }).collect(Collectors.toList());


        return orderBOList;
    }

    public Result getMy(){

        String userId = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();
        UserBO userBO = new UserBO();

        BeanUtils.copyProperties(this.getUser(userId),userBO);


        EntityUser user = getUser(ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId());

        return Result.one("userAccout",getUserAccount()).lambdaPut("userOrderNumber",orderDaoService.lambdaQuery().eq(Order::getUserid,user.getId()).count())
                .lambdaPut("homeOrderNumber",orderDaoService.lambdaQuery().eq(Order::getHousenumberid,user.getHousenumber()).count())
                .lambdaPut("userinfo",userBO)
                .lambdaPut("houseInfo",this.getHouseNumberBo(userId));
    }

    public Result getTitle(){
        return Result.one("title",materialsTitleDaoService.list()).success();
    }
}
