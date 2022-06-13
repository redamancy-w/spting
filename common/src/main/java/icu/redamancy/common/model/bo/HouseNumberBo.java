package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.HouseNumber;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author redamancy
 * @Date 2022/6/8 21:33
 * @Version 1.0
 */
@Getter
@Setter
public class HouseNumberBo extends HouseNumber {

    private Integer unitNumber;
}
