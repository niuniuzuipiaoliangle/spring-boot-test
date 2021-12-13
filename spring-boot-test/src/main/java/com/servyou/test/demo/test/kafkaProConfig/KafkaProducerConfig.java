package com.servyou.test.demo.test.kafkaProConfig;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaConsumerConfig
 *
 * @author syf
 * @desc @TODO
 * @date 2019-09-06 13:29
 **/

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.consumer.servers}")
    private String servers;
    @Value("${kafka.producer.retries:3}")
    private int retries;
    @Value("${kafka.producer.defaultTopic:default}")
    private String defaultTopic;
    @Value("${kafka.producer.batch.size:1048576}")
    private int batchSize;
    @Value("${kafka.producer.linger.ms:10}")
    private int lingerMs;
    @Value("${kafka.producer.buffer.memory:33554432}")
    private long bufferMem;
    @Value("${kafka.producer.acks:all}")
    private String acks;
    @Value("${kafka.producer.buffer.requestTimeout.ms:6000}")
    private Integer requestTimeoutMs;
    @Value("${kafka.producer.compression.type:lz4}")
    private String compressType;


    public KafkaProducerConfig() {
    }

    @Bean
    public DefaultKafkaProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory(producerProperties());
    }

    @Bean
    public Map<String, Object> producerProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        //序列化手段

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //设置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        //达到batchSize大小的时候会发送消息
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        //延时时间，延时时间到达之后计算批量发送的大小没达到也发送消息
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        //缓冲区的值
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMem);
        //producer端的消息确认机制,-1和all都表示消息不仅要写入本地的leader中还要写入对应的副本中
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, PartitionerImpl.class.getName());

        //设置broker响应时间，如果broker在60秒之内还是没有返回给producer确认消息，则认为发送失败
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        //指定拦截器(value为对应的class)
        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.te.handler.KafkaProducerInterceptor");
        //设置压缩算法(默认是木有压缩算法的)
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressType);//snappy

        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory(), true);
        kafkaTemplate.setDefaultTopic(defaultTopic);
        return kafkaTemplate;
    }


    @Bean //创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址                                                                    //kafka的地址，不是zookeeper
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        KafkaAdmin admin = new KafkaAdmin(props);
        return admin;
    }

    @Bean  //kafka客户端，在spring中创建这个bean之后可以注入并且创建topic
    public AdminClient adminClient() {
        return KafkaAdminClient.create(kafkaAdmin().getConfigurationProperties());
    }

}
