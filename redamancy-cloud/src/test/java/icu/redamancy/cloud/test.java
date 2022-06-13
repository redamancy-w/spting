package icu.redamancy.cloud;

import icu.redamancy.common.model.dao.cloud.AccountDaoServiceImpl;
import icu.redamancy.common.model.dao.cloud.MaterialsDaoServiceImpl;
import icu.redamancy.common.model.pojo.cloud.Materials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author redamancy
 * @Date 2022/6/10 17:15
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MaterialsDaoServiceImpl materialsDaoService;

    @Resource
    private AccountDaoServiceImpl accountDaoService;

    @Test
    public void test(){

        accountDaoService.lambdaUpdate().setSql("balance = 100 * consumption").update();

    }


}
