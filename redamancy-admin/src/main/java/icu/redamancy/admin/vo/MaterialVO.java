package icu.redamancy.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialVO {

    private Long id;

    private String name;

    private Integer price;

    private Integer number;

    private Integer limitNumber;

    private Integer soldNumber;

    private String imageList;

    private String filePath;
}
