package icu.redamancy.cloud.rabbitmq;

import icu.redamancy.cloud.properties.RabbitMqProperties;
import icu.redamancy.common.model.dao.cloud.MaterialsDaoServiceImpl;
import icu.redamancy.common.model.pojo.cloud.Account;
import icu.redamancy.common.model.pojo.cloud.Materials;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.spec.PSource;
import java.io.IOException;
import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/11 01:23
 * @Version 1.0
 */
@Component
public class ListenerCloud {
    @Resource
    private MaterialsDaoServiceImpl materialsDaoService;

    @Resource
    private ObjectMapper mapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue(RabbitMqProperties.CLOUD_PROC_TOPIC_KEY))
    @Async
    public void process(@Payload String message) throws IOException {

        System.out.printf(message);

        JsonNode jsonNode = mapper.readTree(message);

        JsonNode data = jsonNode.get("data");
        List<Materials> o = mapper.readValue(data, mapper.getTypeFactory().constructParametricType(List.class, Materials.class));

        if (jsonNode.get("type").asText().equals("DELETE")){

            for (Materials materials : o) {
                redisTemplate.delete("goodlistT:"+materials.getTitleId()+":"+materials.getId());
                redisTemplate.delete("goodlist:"+materials.getId());
            }

        }else {
            for (Materials materials : o) {
                redisTemplate.opsForValue().set("goodlistT:"+materials.getTitleId()+":"+materials.getId(),materials);
                redisTemplate.opsForValue().set("goodlist:"+materials.getId(),materials);
            }
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(RabbitMqProperties.CLOUD_PROC_TOPIC_ACCOUNT_KEY))
    @Async
    public void processAccount(@Payload String message) throws IOException {

        JsonNode jsonNode = mapper.readTree(message);
        JsonNode data = jsonNode.get("data");

        List<Account> o = mapper.readValue(data, mapper.getTypeFactory().constructParametricType(List.class, Account.class));

        if (jsonNode.get("type").asText().equals("DELETE")){

            for (Account account : o) {
                redisTemplate.delete("account:"+account.getHousenumberid());
            }

        }else {

            for (Account account : o) {
                redisTemplate.opsForValue().set("account:"+account.getHousenumberid(),account);

            }
        }
    }
}
