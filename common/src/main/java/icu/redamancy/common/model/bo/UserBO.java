package icu.redamancy.common.model.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author redamancy
 * @Date 2022/6/8 22:33
 * @Version 1.0
 */
@Data
public class UserBO {
    private Long id;

    private Integer unit;

    private String name;

    private Long createTime;
    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户电话
     */
    private Integer phoneNumber;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatarUrl;

    private int state;

    private Long housenumber;

}
