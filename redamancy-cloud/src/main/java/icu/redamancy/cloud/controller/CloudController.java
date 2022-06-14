package icu.redamancy.cloud.controller;

import com.alibaba.druid.support.ibatis.SqlMapClientImplWrapper;
import com.baomidou.mybatisplus.annotation.TableField;
import icu.redamancy.cloud.service.CloudService;
import icu.redamancy.common.model.bo.DeclareBo;
import icu.redamancy.common.model.bo.OrderBO;
import icu.redamancy.common.model.dto.BuyGoodsListDTO;
import icu.redamancy.common.model.dto.DDeclareDTO;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.model.pojo.cloud.EntityDeclare;
import icu.redamancy.common.model.pojo.cloud.HouseNumber;
import icu.redamancy.common.model.pojo.cloud.Unit;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.result.ResponseResult;
import icu.redamancy.common.utils.result.Result;
import icu.redamancy.common.utils.result.ResultCode;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import javax.ws.rs.POST;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author redamancy
 * @Date 2022/6/7 09:50
 * @Version 1.0
 */

@RestController
@ResponseResult
public class CloudController {

    @Autowired
    private CloudService cloudService;


    @PostMapping("cloud")
    public Result addCloud(DDeclareDTO declare,
                                        @RequestParam(name = "file") MultipartFile[] file) {


        Boolean isOk = false;

        try {
            isOk = cloudService.addDeclare(declare, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!cloudService.addStay(declare))
            throw  new BaseException(ResultCode.FEEDBACK.code(),"添加失败");

        if (!cloudService.addAccount(declare))
            throw  new BaseException(ResultCode.FEEDBACK.code(),"添加失败");
        if (isOk) {
            return Result.one("msg","成功").success();
        } else {
            throw new BaseException(ResultCode.INTERNAL_SERVER_ERROR.code(), "失败");
        }
    }

    @GetMapping("cloud")
    public Map<String, Object> getDeclare(@RequestParam(name = "current") Integer current,
                                          @RequestParam(name = "size") Integer size,
                                          @RequestParam(name = "updateTime") Long updateTime) {

        Map<String, Object> map = new HashMap<>();
        List<DeclareBo> declareBos = cloudService.getDeclare(current, size, updateTime);
        map.put("declareList", declareBos);
        return map;

    }

    @GetMapping("home")
    public Map<String,Object> initHome(){

           return cloudService.initHome();

    }


    /**
     * 添加门牌号
     * @param unit
     * @param houseNumber
     * @return
     */
    @PostMapping("housenumber")
    public Map<String, Object> addHouseNumber(@RequestParam(name = "unit") int unit, @RequestParam(name = "houseNumber") String houseNumber) {

        Boolean isOk = cloudService.addHouseNumber(new HouseNumber().setHousNumber(houseNumber), new Unit().setUnit(unit));
        Map<String, Object> map = new HashMap<>();

        if (isOk) {
            map.put("msg","成功");
            return map;
        } else {
            throw new BaseException(ResultCode.FEEDBACK.code(), "添加失败");
        }
    }

    /**
     * 获得门牌号列表
     * @return
     */
    @GetMapping(value = "housenumber")
    public Map<String,Object> getHouseNumber(){
        Map<String,Object> map = new HashMap<>();
        map.put("houseNumberList",cloudService.getHouseNumberList());
        return map;
    }

    @GetMapping(value = "goods")
    public Result getGoodsList(){

        return Result.one("doogsList",cloudService.getMaterialsList())
                .lambdaPut("account",cloudService.getUserAccount())
                .success();
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = "buyGoods")
    public Result buyGoods(@RequestBody()List<BuyGoodsListDTO> buyGoodsListDTOS) throws InterruptedException {

        Boolean isOk = cloudService.BuyGoods(buyGoodsListDTOS);
        if (isOk){
            return Result.one("msg","购买成功");
        }
        return Result.one("msg","购买失败");
    }

    @GetMapping(value = "userOrder")
    public Result getOrder(String type){
        return Result.one("order",cloudService.getOrder()).success();
    }

    @GetMapping(value = "homeOrder")
    public Result getOrder_home(){
        return Result.one("order",cloudService.getOrder_home()).success();
    }

    @GetMapping(value = "my")
    public Result getMy(){
        return cloudService.getMy();
    }

    @GetMapping(value = "materialsTitle")
    public Result getTitle(){
        return cloudService.getTitle();
    }
}
