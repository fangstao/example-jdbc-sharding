package com.example.jdbc.sharding.event;

import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.example.jdbc.sharding.domain.Order;
import com.example.jdbc.sharding.repository.OrderRepository;

import javax.annotation.Resource;
import java.util.Objects;

/**
 *
 * Created by fangtao on 16/9/11.
 */
public class OrderCreatedChecker implements MessageChecker {
    @Resource
    private OrderRepository orderRepository;

    @Override
    public boolean accept(EventBase eventBase) {
        return OrderCreatedEvent.class.isAssignableFrom(eventBase.getClass());
    }

    @Override
    public TransactionStatus check(EventBase eventBase) {
        OrderCreatedEvent event = (OrderCreatedEvent) eventBase;
        Order order = orderRepository.findOne(event.getOrderId(), event.getUserId());
        return Objects.nonNull(order) ? TransactionStatus.CommitTransaction : TransactionStatus.RollbackTransaction;
    }
}
