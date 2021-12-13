package com.servyou.test.demo.test.kafkaConConfig;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.backoff.FixedBackOff;

import java.util.function.BiConsumer;

/**
 * @author 聪聪只爱笨笨
 */
@Slf4j
//@Configuration
public class KafkaConfiguration {
//	@Bean
//	@Primary
	public SeekToCurrentErrorHandler seekToCurrentErrorHandler() {
		SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler(new BiConsumer<ConsumerRecord<?, ?>, Exception>() {
			@Override
			public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
				//重试4次仍然失败后会进去BiConsumer接口的accept方法，可以进行保存数据库等操作
				log.error("消费失败4次,消息保存到数据库. record:{}", JSON.toJSONString(consumerRecord));
			}
		}, new FixedBackOff(0L, 3));//interval 为0标识立即重试，maxAttempts为4标识最多重试四次
		return errorHandler;
	}
}