package com.example.jdbc.sharding.event;

import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

/**
 * Created by fangtao on 16/9/11.
 */
public interface MessageChecker {
    boolean accept(EventBase eventBase);

    TransactionStatus check(EventBase eventBase);
}
