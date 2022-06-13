package icu.redamancy.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import icu.redamancy.common.model.dao.cloud.mapper.MaterialMapper;
import icu.redamancy.common.model.pojo.cloud.Material;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedamancyAdminApplicationTests {

    @Autowired
    private MaterialMapper materialMapper;
    @Test
    public void test1(){
        int update = materialMapper.update(null,
                new LambdaUpdateWrapper<Material>()
                        .eq(Material::getId, 1)
                        .set(Material::getPrice, 20)
                        .set(Material::getNumber, 20));
        System.out.println(update);
    }
}
