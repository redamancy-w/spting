package icu.redamancy.common.utils.lock;

import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 17:46
 * @Version 1.0
 */
public interface Ilock {


    Boolean tryLock(String name, Long time);

    void unlock(String name);
}
