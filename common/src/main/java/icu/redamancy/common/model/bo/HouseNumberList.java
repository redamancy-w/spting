package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.HouseNumber;
import icu.redamancy.common.model.pojo.cloud.Unit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/9 15:00
 * @Version 1.0
 */
@Getter
@Setter
public class HouseNumberList extends Unit {
    private List<HouseNumber> houseNumbers;
}
