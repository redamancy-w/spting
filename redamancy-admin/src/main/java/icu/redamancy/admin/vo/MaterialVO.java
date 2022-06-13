package icu.redamancy.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialVO {

    private String name;

    private String price;

    private Integer number;

    private Integer limitNumber;

    private Integer soldNumber;

    private String imageList;
}
