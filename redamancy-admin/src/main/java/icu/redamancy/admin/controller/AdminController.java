package icu.redamancy.admin.controller;

import icu.redamancy.admin.service.AdminService;
import icu.redamancy.admin.utils.Result;
import icu.redamancy.common.model.pojo.cloud.Materials;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 审批申请
     * @param id 审批表的id
     * @param state user表state： 0-未提交 1-绿 2-红 3-未审批
     * @return
     */
    @PostMapping("declare/updateDeclare")
    public Result updateDeclare(@RequestParam("id") Long id, @RequestParam("state") Integer state){

        return adminService.updateDeclare(id, state);
    }

    /**
     * 查询所有用户信息并分页
     * @param current 页数
     * @param size 每页数据量
     * @param updateTime 每页最后一个数据的时间
     * @return
     */
    @PostMapping("user/getUser")
    public Result getUser(@RequestParam(name = "current") Integer current,
                          @RequestParam(name = "size") Integer size,
                          @RequestParam(name = "updateTime") Long updateTime){

        return adminService.getUser(current, size, updateTime);
    }

    /**
     * 更改用户状态
     * @param state 状态(2-绿， 3-红）
     * @param userId 用户id
     * @return
     */
    @PostMapping("user/updateUser")
    public Result updateUserById(@RequestParam("state") Integer state, @RequestParam("userId") Long userId){

        return adminService.updateUserById(userId, state);
    }

    /**
     * 添加物资
     * @return
     * @throws IOException
     */
    @PostMapping("material/addMaterial")
    public Result addMaterial(String titleId, @RequestParam(name = "name") String name, @RequestParam(name = "price") Integer price, @RequestParam(name = "number") Integer number, @RequestParam(name = "limitNumber") Integer limitNumber, @RequestParam MultipartFile[] file) throws IOException {
        Materials material = new Materials();
        material.setName(name);
        material.setPrice(price);
        material.setNumber(number);
        material.setLimitNumber(limitNumber);
        return adminService.addMaterial(titleId, material, file);
    }

    /**
     * 获取未审批的审批申请表
     * @param current 每页有几条数据，可以写死
     * @param size 页数
     * @param updateTime 现在的时间
     * @return
     */
    @PostMapping("declare/getDeclare")
    public Result getDeclare(@RequestParam(name = "current") Integer current,
                                          @RequestParam(name = "size") Integer size,
                                          @RequestParam(name = "updateTime") Long updateTime) {

        return adminService.getDeclare(current, size, updateTime);
    }

    /**
     * 获取所有的物资
     * @return
     */
    @PostMapping("material/getMaterial")
    public Result getMaterial(){

        return adminService.getMaterial();
    }

    /**
     * 更新物资的价格数量
     * @param id 物资id
     * @param price 物资价格
     * @param number 物资数量
     * @return
     */
    @PostMapping("material/updateMaterial")
    public Result updateMaterial(@RequestParam("id") Long id,@RequestParam("price") Integer price, @RequestParam("number") Integer number){

        return adminService.updateMaterial(id, price, number);
    }

    /**
     * 获取待处理和处理的审批数量
     * @return
     */
    @PostMapping("declare/getStateCount")
    public Result getDeclareStateCount(){

        return adminService.getDeclareStateCount();
    }

    /**
     * 获取红绿码的数量
     * @return
     */
    @PostMapping("user/getStateCount")
    public Result getUserStateCount(){
        return adminService.getUserStateCount();
    }

    /**
     * 获取物资概况（已售数量和剩余数量）
     * @return
     */
    @PostMapping("material/getMaterialNumber")
    public Result getMaterialNumber(){

        return adminService.getMaterialNumber();
    }

    /**
     * 获取审批好之后的时间
     * @return
     */
    @PostMapping("declare/getDeclareTime")
    public Result getDeclareTime(){

        return adminService.getDeclareTime();
    }

    /**
     * 物资申请表的审批
     * @param id
     * @return
     */
    @PostMapping("order/updateOrder")
    public Result updateOrder(@RequestParam("id") Long id,@RequestParam("state")Integer state){
        return adminService.updateOrder(id,state);
    }

    /**
     * 物资申请表
     * @return
     */
    @PostMapping("order/getOrder")
    public Result getOrder(){
        return adminService.getOrder();
    }



    /**
     * 获取单元门牌
     * @return
     */
    @PostMapping("unit/getUnit")
    public Result getUnit(){
        return adminService.getUnit();
    }



    @PostMapping("deletematerials")
    public Result getDeleteMaterials(@RequestParam("id")Long id){
        if (adminService.deleteMaterials(id)){
            return Result.success(null);
        }
        throw new BaseException(ResultCode.FEEDBACK);
    }


}
