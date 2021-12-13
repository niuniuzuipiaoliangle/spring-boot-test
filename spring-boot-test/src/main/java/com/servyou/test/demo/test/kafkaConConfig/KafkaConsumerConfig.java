package com.servyou.test.demo.test.kafkaConConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaConsumerConfig
 *
 * @author syf
 * @desc @TODO
 * @date 2019-09-06 13:29
 **/
@Slf4j
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.servers}")
    private String servers;
    @Value("${kafka.consumer.session.timeout}")
    private int sessionTimeout;
    @Value("${kafka.consumer.maxPollIntervalMs}")
    private int maxPollIntervalMs;
    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;
    @Value("${kafka.consumer.group.id}")
    private String groupId;
    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;
    @Value("${kafka.consumer.autoStartup:true}")
    private boolean autoStartup;
    @Value("${kafka.consumer.autoCommit:false}")
    private boolean autoCommit;
    @Value("${kafka.consumer.maxPollSize:20}")
    private Integer maxPollSize;

    @Bean(name = "dataListener")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> dataListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> configMap = consumerConfigs();
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(configMap));
        factory.setConcurrency(concurrency);
        factory.setBatchListener(true);
        factory.setAutoStartup(autoStartup);
        if (!autoCommit) {
            //使用spring-kafka自动提交的方式，3秒提交一次
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.TIME);
            factory.getContainerProperties().setAckTime(3000);
        }
        return factory;
    }

    @Bean(name = "stringDataListener")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> stringDataListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> configMap = consumerConfigs();
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(configMap));
        factory.setConcurrency(concurrency);
        factory.setAutoStartup(autoStartup);
        // 最大重试次数
        SeekToCurrentErrorHandler seekToCurrentErrorHandler = new SeekToCurrentErrorHandler((consumerRecord, e) -> {
            log.error("异常.抛弃这个消息============,{}", consumerRecord.toString());
        }, new FixedBackOff(5000, 6));
        factory.setErrorHandler(seekToCurrentErrorHandler);

        //设置提交偏移量的方式 ,否则出现异常的时候, 会报错No Acknowledgment available as an argument, the listener container must have a MANUAL AckMode to populate the Acknowledgment.
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        if (!autoCommit) {
            //使用spring-kafka自动提交的方式，3秒提交一次
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.TIME);
            factory.getContainerProperties().setAckTime(3000);
        }
        return factory;
    }

    @Bean(name = "stringBatchDataListener")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> stringBatchDataListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> configMap = consumerConfigs();
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(configMap));
        factory.setConcurrency(concurrency);
        factory.setBatchListener(true);
        factory.setAutoStartup(autoStartup);
        if (!autoCommit) {
            //使用spring-kafka自动提交的方式，3秒提交一次
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.TIME);
            factory.getContainerProperties().setAckTime(3000);
        }
        return factory;
    }



    private Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, (sessionTimeout / 4) + "");
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollSize);
        return propsMap;
    }


}