package icu.redamancy.common.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/10 14:52
 * @Version 1.0
 */

@Data
public class BuyGoodsListDTO {

    private String buyNum;

    private String createTime;

    private String updateTime;

    private String id;

    private String imageList;

    private String limitNumber;

    private String name;

    private String price;

    private String soldNumber;

    private String titleId;
}
