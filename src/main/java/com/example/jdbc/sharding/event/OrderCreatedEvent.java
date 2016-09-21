package com.example.jdbc.sharding.event;

/**
 * Created by fangtao on 16/9/11.
 */
public class OrderCreatedEvent extends EventBase {
    public static final String TOPIC = "ft-order-topic";
    private Long orderId;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public static OrderCreatedEvent create(Long orderId, Long userId) {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(orderId);
        event.setUserId(userId);
        return event;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
