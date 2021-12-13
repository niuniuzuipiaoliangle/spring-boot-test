package com.servyou.test.demo.test.kafkaProConfig;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * PartitionerImpl
 *
 * @author syf
 * @desc @TODO
 * @date 2020-04-23 15:00
 **/

public class PartitionerImpl extends DefaultPartitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (StringUtils.isEmpty(key)) return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
        String keyObj = (String) key;
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
        int partitionCount = partitionInfoList.size();
        //System.out.println("partition size: " + partitionCount);
        int hash = HashUtils.hash(keyBytes);
        int partionId = hash % partitionCount;
        return partionId;
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void configure(Map<String, ?> map) {
        super.configure(map);
    }
}
