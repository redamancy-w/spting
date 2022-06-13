package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.AccountMapper;
import icu.redamancy.common.model.dao.cloud.service.AccountService;
import icu.redamancy.common.model.pojo.cloud.Account;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 13:56
 * @Version 1.0
 */
@Service
public class AccountDaoServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
