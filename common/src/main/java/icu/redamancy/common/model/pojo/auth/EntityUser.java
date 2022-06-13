package icu.redamancy.common.model.pojo.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/5/6 14:03
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "fang_user")
public class EntityUser extends BaseEntity {


    @TableField(value = "name")
    private String name;

    /**
     * 用户名称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户电话
     */
    @TableField(value = "phoneNumber")
    private Integer phoneNumber;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 用户头像
     */
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * openid
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 用户城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 用户国家
     */
    @TableField(value = "country")
    private String country;

    @TableField(value = "state")
    private int state;

    @TableField(value = "housenumberid")
    private Long housenumber;

}
