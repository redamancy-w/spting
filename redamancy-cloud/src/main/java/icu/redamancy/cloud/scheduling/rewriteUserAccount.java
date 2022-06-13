package icu.redamancy.cloud.scheduling;

import icu.redamancy.common.model.dao.cloud.AccountDaoServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author redamancy
 * @Date 2022/6/11 14:08
 * @Version 1.0
 */

@Component
public class rewriteUserAccount {



    @Resource
    private AccountDaoServiceImpl accountDaoService;

    /**
     * 定时任务，每三天晚上的0点更新用户金额
     */
    @Scheduled(cron = "0 0 0 1/3 * ?")
    public void updateAccount(){

        accountDaoService.lambdaUpdate().setSql("balance = 100 * consumption").update();

    }




}
