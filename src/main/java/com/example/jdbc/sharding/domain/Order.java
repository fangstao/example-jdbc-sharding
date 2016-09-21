package com.example.jdbc.sharding.domain;

/**
 * Created by fangtao on 16/9/5.
 */
public class Order {
    private Long id;
    private Long userId;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Order create(Long orderId, long userId, String status) {
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus(status);
        return order;
    }
}
