package com.servyou.test.demo.test.kafkaProConfig;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.List;

/**
 * 预警级别消息生成器
 *
 * @author syf
 * @desc @TODO
 * @date 2020-04-22 19:03
 **/

@Component
@Log4j2
public class YjxxMsgProducer<T> {
    @Value("${fpzsjk.fpkj.yjjbts.kafka.enabled:true}")
    private boolean enabled;

    @Autowired
    private KafkaTemplate<String, String> producer;


    public void pushKafka(List<T> object) {
        //构建待发送的消息
        if (!enabled) {
            log.info("预警信息推送开关已关闭。${fpzsjk.fpkj.yjjbts.kafka.enabled:false}");
            return;
        }

        String objectJson = JsonUtil.toJSONString(object);

        //尝试发送消息
        ListenableFuture future = producer.send(ITopicName.test, objectJson);

        future.addCallback(
                new SuccessCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        log.debug("预警信息消息推送成功。");
                    }
                },
                new FailureCallback() {
                    @Override
                    public void onFailure(Throwable e) {
                        log.error("预警信息消息推送失败。", e);
                    }
                });
    }
}

