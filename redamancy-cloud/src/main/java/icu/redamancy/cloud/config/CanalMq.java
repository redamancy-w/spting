package icu.redamancy.cloud.config;

import icu.redamancy.cloud.properties.RabbitMqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author redamancy
 * @Date 2022/6/11 01:13
 * @Version 1.0
 */
@Configuration
public class CanalMq implements BeanPostProcessor {



    @Bean
    Queue cloudQUEUES() {
        return new Queue(RabbitMqProperties.CLOUD_PROC_TOPIC_QUEUES, true, false, false);
    }

    @Bean
    DirectExchange cloudEXCHANGE() {
        return new DirectExchange(RabbitMqProperties.CLOUD_CANAL_EXCHANGE, true, false);
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(cloudQUEUES())
                .to(cloudEXCHANGE())
                .with(RabbitMqProperties.CLOUD_PROC_TOPIC_KEY);
    }

//account

    @Bean
    Queue cloudAccountQUEUES() {
        return new Queue(RabbitMqProperties.CLOUD_PROC_TOPIC_ACCOUNT_QUEUES, true, false, false);
    }

    @Bean
    Binding bindingAccount() {
        return BindingBuilder
                .bind(cloudAccountQUEUES())
                .to(cloudEXCHANGE())
                .with(RabbitMqProperties.CLOUD_PROC_TOPIC_ACCOUNT_KEY);
    }

}
