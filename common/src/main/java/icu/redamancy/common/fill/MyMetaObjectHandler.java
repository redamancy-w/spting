package icu.redamancy.common.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author redamancy
 * @Date 2022/5/9 20:11
 * @Version 1.0
 */

//mybaitpuls 的自动生成
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        long now = System.currentTimeMillis();

        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long now = System.currentTimeMillis();

        this.setFieldValByName("updateTime", now, metaObject);
    }
}
