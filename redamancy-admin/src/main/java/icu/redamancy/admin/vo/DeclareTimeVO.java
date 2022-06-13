package icu.redamancy.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeclareTimeVO {

    private String name;

    private Long updateTime;

    private Integer state;
}
