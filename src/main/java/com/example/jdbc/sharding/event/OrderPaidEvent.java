package com.example.jdbc.sharding.event;

/**
 * order paid event
 * Created by fangtao on 16/9/5.
 */
public class OrderPaidEvent extends EventBase{
    public static final String TOPIC = "order_paid_event";
    private Long   orderId;

}
