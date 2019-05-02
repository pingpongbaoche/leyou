package com.leyou.sms.mq;

import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Map;

/**
 * 消息监听器。监听生产者方发送的消息
 * */
@Slf4j
@Component
@EnableConfigurationProperties({SmsProperties.class})
public class SmsListener {
    @Autowired
    private SmsProperties prop;
    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送短信验证码
     * */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(name = "ly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = "sms.verify.code"
    ))
    public void listenInsertOrUpdate(Map<String,String> msg){
        if(CollectionUtils.isEmpty(msg)){
            return;
        }
        String phone = msg.remove("phone");//remove获取并且删除元素,map中剩下是code
        if(StringUtils.isBlank(phone)){
            return;
        }
        //发短信
        smsUtils.sendSms(phone,prop.getSignName(),prop.getVerifyCodeTemplate(), JsonUtils.serialize(msg));

    }

}
