package com.example.jdbc.sharding.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.example.jdbc.sharding.event.CompositeTransactionChecker;
import com.example.jdbc.sharding.event.MessageChecker;
import com.example.jdbc.sharding.event.OrderCreatedChecker;
import com.google.common.collect.Lists;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by fangtao on 16/9/11.
 */
@Configuration
public class MessageConfig {
    @Resource
    private FSTConfiguration fstConfiguration;

    @Bean(initMethod = "start")
    public Producer producer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "PID-order-producer");
        properties.put(PropertyKeyConst.AccessKey, "tKfyDVAcolJVlH8a");
        properties.put(PropertyKeyConst.SecretKey, "gpJ0PTJrxIPhmvman6Cij7tVqayL3I");
        Producer producer = ONSFactory.createProducer(properties);
        return producer;
    }

    @Bean(initMethod = "start")
    public TransactionProducer transactionProducer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "PID-order-producer");
        properties.put(PropertyKeyConst.AccessKey, "tKfyDVAcolJVlH8a");
        properties.put(PropertyKeyConst.SecretKey, "gpJ0PTJrxIPhmvman6Cij7tVqayL3I");
        TransactionProducer producer = ONSFactory.createTransactionProducer(properties, transactionChecker());
        return producer;
    }

    @Bean(initMethod = "start")
    public Consumer consumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID-ft-order-consumer");
        properties.put(PropertyKeyConst.AccessKey, "tKfyDVAcolJVlH8a");
        properties.put(PropertyKeyConst.SecretKey, "gpJ0PTJrxIPhmvman6Cij7tVqayL3I");
        Consumer consumer = ONSFactory.createConsumer(properties);
        return consumer;
    }

    @Bean
    public CompositeTransactionChecker transactionChecker() {
        ArrayList<MessageChecker> messageCheckers = Lists.newArrayList();
        messageCheckers.add(new OrderCreatedChecker());
        return new CompositeTransactionChecker(messageCheckers, fstConfiguration);
    }

}
