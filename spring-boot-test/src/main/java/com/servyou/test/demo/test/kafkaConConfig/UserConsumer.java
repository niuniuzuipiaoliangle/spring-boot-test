package com.servyou.test.demo.test.kafkaConConfig;

import com.servyou.test.demo.test.entity.User;
import com.servyou.test.demo.test.kafkaProConfig.ITopicName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author syf
 * @desc 预警信息推送税控
 * @date 2020-04-23 16:33
 **/

@Component
@Slf4j
public class UserConsumer {

    private final String sufix = "yjjb2sc2";
    private final String sufix1 = "yjjb2sc-001";

    @Value("${fpszjk.fpkj.duplicate.yjxx.enabled:true}")
    private boolean yjxxDup;
    /**
     * Receive.
     *
     * @param message the message
     */

    int i =0;
    @KafkaListener(topics = {ITopicName.test},
            containerFactory = "stringDataListener", groupId = sufix,topicPattern = "1")
    public void receive(String message) throws Exception {
        System.out.println();
        List<User> zbdfList = JsonUtil.parseJSONList(message, User.class);
        System.out.println(message);
//        System.out.println(seek.isAckAfterHandle());
        throw new Exception("异常消费，重试第 >>"+(i++));
    }
}
