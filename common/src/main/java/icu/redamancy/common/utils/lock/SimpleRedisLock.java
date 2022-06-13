package icu.redamancy.common.utils.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author redamancy
 * @Date 2022/6/10 17:48
 * @Version 1.0
 */
@Service
public class SimpleRedisLock implements Ilock{


    private static final String KEY_PATH = "lock";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean tryLock(String name, Long time) {

        long threadId = Thread.currentThread().getId();
        Boolean success = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(KEY_PATH+":"+name ,threadId+"",time, TimeUnit.MILLISECONDS);

        return success;
    }


    @Override
    public void unlock(String name) {

        stringRedisTemplate.delete(KEY_PATH+":"+name);
    }
}
