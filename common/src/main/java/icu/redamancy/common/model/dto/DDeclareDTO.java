package icu.redamancy.common.model.dto;

import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author redamancy
 * @Date 2022/6/11 15:25
 * @Version 1.0
 */

@Data
public class DDeclareDTO {

    private String name;

    private String mobile;

    private String sex;

    private String addresses;

    private String idcard;

    private String pictureList;

    private String relation;

    private String housenumberid;

    private String population;
}
