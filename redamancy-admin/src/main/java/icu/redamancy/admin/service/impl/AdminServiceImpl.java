package icu.redamancy.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.redamancy.admin.service.AdminService;
import icu.redamancy.admin.utils.Result;
import icu.redamancy.admin.vo.*;
import icu.redamancy.common.feignclient.PictureClient;
import icu.redamancy.common.model.bo.DeclareBo;
import icu.redamancy.common.model.bo.UserBO;
import icu.redamancy.common.model.dao.auth.UserDaoServiceImpl;
import icu.redamancy.common.model.dao.auth.mapper.UserMapper;
import icu.redamancy.common.model.dao.cloud.DeclareDaoServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.DeclareMapper;
import icu.redamancy.common.model.dao.cloud.mapper.MaterialsMapper;
import icu.redamancy.common.model.dao.cloud.service.HouseNumberService;
import icu.redamancy.common.model.dao.cloud.service.UnitService;
import icu.redamancy.common.model.dao.picture.service.PictureService;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.model.pojo.cloud.EntityDeclare;
import icu.redamancy.common.model.pojo.cloud.HouseNumber;
import icu.redamancy.common.model.pojo.cloud.Materials;
import icu.redamancy.common.model.pojo.cloud.Unit;
import icu.redamancy.common.model.pojo.picture.Picture;
import icu.redamancy.common.utils.jjwt.ParsingUserJwtInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Value("${spring.application.name}")
    private String objectName;

    @Autowired
    private DeclareMapper declareMapper;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private PictureClient pictureClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MaterialsMapper materialsMapper;

    @Autowired
    private DeclareDaoServiceImpl declareDaoService;

    @Resource
    private UserDaoServiceImpl userDaoService;

    @Autowired
    private PictureService pictureService;

//    @Autowired
//    private HouseNumberMapper houseNumberMapper;
//
//    @Autowired
//    private UnitMapper unitMapper;

    @Autowired
    private HouseNumberService houseNumberService;

    @Autowired
    private UnitService unitService;

    private int updateUser(Long userId, Integer state){
        int update = userMapper.update(null,
                new UpdateWrapper<EntityUser>().eq("id", userId).set("state", state));
        return update;
    }

    @Override
    public Result updateDeclare(Long id, int state) {

        String userId = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();
        int updateUser = updateUser(Long.valueOf(userId), state);
        int updateDeclare = declareMapper.update(null,
                new UpdateWrapper<EntityDeclare>().eq("id", id).set("state", 1));
        if (updateUser > 0 && updateDeclare >0){
            return Result.success(null);
        } else {
            return Result.fail(400,"审批失败");
        }
    }
    
    @Override
    public Result addMaterial(Materials material, MultipartFile[] file) throws IOException {
        String userId = ParsingUserJwtInfo.GetParsingUserJwtInfo().getUserId();

        
        List<Long> idList = pictureClient.addPicture(objectName, userId, file);
        String idJson = objectMapper.writeValueAsString(idList);
        material.setImageList(idJson);
        int insert = materialsMapper.insert(material);
        if (insert > 0){
            return Result.success(null);
        } else {
            return Result.fail(400, "添加物资失败");
        }
    }

    @Override
    public Result getDeclare(Integer current, Integer size, Long updateTime) {

        Page<EntityDeclare> page = declareDaoService
                .lambdaQuery()
                .orderByDesc(EntityDeclare::getUpdateTime)
                .lt(EntityDeclare::getUpdateTime, updateTime)
                .eq(EntityDeclare::getState,0)
                .page(new Page<>(1, size));

        List<DeclareBo> declareBos = page.getRecords().stream().map(v -> {
            DeclareBo declareBo = new DeclareBo();
            BeanUtils.copyProperties(v, declareBo);
            if (!ObjectUtils.isEmpty(v.getPictureList()) && !v.getPictureList().equals("null")) {
                List<Long> id = JSON.parseArray(v.getPictureList(), Long.class);
                List<Picture> pictureList = pictureService.listByIds(id);
                declareBo.setPictureList(String.valueOf(pictureList));
            }

            return declareBo;

        }).collect(Collectors.toList());
        return Result.success(declareBos);
    }

    @Override
    public Result getUser(Integer current, Integer size, Long updateTime) {
        Page<EntityUser> page = userDaoService
                .lambdaQuery()
                .orderByDesc(EntityUser::getUpdateTime)
                .lt(EntityUser::getUpdateTime, updateTime)
                .page(new Page<>(1, size));

        List<UserBO> collect = page.getRecords().stream().filter(v -> !Objects.isNull(v.getHousenumber())).map(v -> {
            UserBO userBo = new UserBO();
            BeanUtils.copyProperties(v, userBo);

            Long houseNumberId = v.getHousenumber();
            HouseNumber houseNumberById = houseNumberService.getById(houseNumberId);

            Long unitId = houseNumberById.getUnit();
            Unit unitById = unitService.getById(unitId);

            userBo.setHousenumber(Long.valueOf(houseNumberById.getHousNumber()));
            userBo.setUnit(unitById.getUnit());

            return userBo;
        }).collect(Collectors.toList());
        return Result.success(collect);
    }

    @Override
    public Result updateUserById(Long userId, Integer state) {
        int updateUser = updateUser(userId, state);
        if (updateUser > 0){
            return Result.success(null);
        } else {
            return Result.fail(400,"修改失败");
        }
    }

    @Override
    public Result processMaterial(Long id) {
        return null;
    }

    @Override
    public Result getMaterial() {
        List<Materials> materialList = materialsMapper.selectList(null);
        List<MaterialVO> collect = materialList.stream().map(v -> {
            MaterialVO materialVO = new MaterialVO();
            BeanUtils.copyProperties(v, materialVO);
            return materialVO;
        }).collect(Collectors.toList());
        return Result.success(collect);
    }

    @Override
    public Result updateMaterial(Long id, int price, int number) {
        int update = materialsMapper.update(null,
                new LambdaUpdateWrapper<Materials>()
                        .eq(Materials::getId, id)
                        .set(Materials::getPrice, price)
                        .set(Materials::getNumber, number));
        if (update > 0){
            return Result.success(null);
        }else {
            return Result.fail(400,"更新物资失败");
        }
    }

    @Override
    public Result getDeclareStateCount() {
        Long notDealCount = declareMapper.selectCount(
                new LambdaUpdateWrapper<EntityDeclare>().eq(EntityDeclare::getState, 0));
        Long dealCount = declareMapper.selectCount(
                new LambdaUpdateWrapper<EntityDeclare>().eq(EntityDeclare::getState, 1));
        DeclareStateCountVO countVO = new DeclareStateCountVO();
        countVO.setDealCount(dealCount);
        countVO.setNotDealCount(notDealCount);
        return Result.success(countVO);
    }

    @Override
    public Result getUserStateCount() {
        Long greenCount = userMapper.selectCount(
                new LambdaUpdateWrapper<EntityUser>().eq(EntityUser::getState, 2));
        Long redCount = userMapper.selectCount(
                new LambdaUpdateWrapper<EntityUser>().eq(EntityUser::getState, 3));
        UserStateCountVO userStateCountVO = new UserStateCountVO();
        userStateCountVO.setRedCount(redCount);
        userStateCountVO.setGreenCount(greenCount);
        return Result.success(userStateCountVO);
    }

    @Override
    public Result getMaterialNumber() {
        List<Materials> materials = materialsMapper.selectList(
                new LambdaQueryWrapper<Materials>()
                        .select(Materials::getName, Materials::getSoldNumber, Materials::getNumber));

        List<MaterialNumberVO> collect = materials.stream().map(v -> {
            MaterialNumberVO materialNumberVO = new MaterialNumberVO();
            BeanUtils.copyProperties(v, materialNumberVO);
            return materialNumberVO;
        }).collect(Collectors.toList());

        return Result.success(collect);
    }

    @Override
    public Result getDeclareTime() {
        List<EntityDeclare> entityDeclares = declareMapper.selectList(
                new LambdaQueryWrapper<EntityDeclare>()
                        .eq(EntityDeclare::getState, 1)
                        .select(EntityDeclare::getName, EntityDeclare::getUpdateTime)
                        .orderByDesc(EntityDeclare::getUpdateTime));

        List<EntityUser> entityUsers = userMapper.selectList(new LambdaQueryWrapper<>());

        List<DeclareTimeVO> collectDeclare = entityDeclares.stream().map(v -> {
            DeclareTimeVO declareTimeVO = new DeclareTimeVO();
            BeanUtils.copyProperties(v, declareTimeVO);
            List<EntityUser> collectUser = entityUsers.stream()
                    .filter(entityUser -> entityUser.getName().equals(v.getName()))
                    .collect(Collectors.toList());
            Integer state = collectUser.get(0).getState();
            declareTimeVO.setState(state);

            return declareTimeVO;

        }).collect(Collectors.toList());

        return Result.success(collectDeclare);
    }



}
