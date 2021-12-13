package com.servyou.test.demo.test.kafkaConConfig;

import com.sun.istack.internal.Nullable;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.FailedRecordProcessor;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekUtils;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;
import java.util.function.BiConsumer;

public class SeekToCurrentErrorHandler extends FailedRecordProcessor implements ContainerAwareErrorHandler {
    private boolean ackAfterHandle;

	//默认是失败后重试9次，没有BiConsumer接口的实现也就是没有后续处理逻辑
    public SeekToCurrentErrorHandler() {
        this((BiConsumer)null, SeekUtils.DEFAULT_BACK_OFF);//DEFAULT_BACK_OFF = new FixedBackOff(0L, 9L)
    }

    public SeekToCurrentErrorHandler(BackOff backOff) {
        this((BiConsumer)null, backOff);
    }

    public SeekToCurrentErrorHandler(BiConsumer<ConsumerRecord<?, ?>, Exception> recoverer) {
        this(recoverer,new FixedBackOff(0L, 3));//DEFAULT_BACK_OFF = new FixedBackOff(0L, 9L)
    }

	public SeekToCurrentErrorHandler(@Nullable BiConsumer<ConsumerRecord<?, ?>, Exception> recoverer, BackOff backOff) {
		super(recoverer, backOff);
		this.ackAfterHandle = true;
	}

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {

    }

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {

    }

    @Override
    public boolean isAckAfterHandle() {
        return false;
    }

    @Override
    public void setAckAfterHandle(boolean ack) {

    }

    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer) {

    }

    @Override
    public void handle(Exception e, List<ConsumerRecord<?, ?>> list, Consumer<?, ?> consumer, MessageListenerContainer messageListenerContainer) {
        System.out.println(111);
    }
}