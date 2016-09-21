package com.example.jdbc.sharding.service;

import com.example.jdbc.sharding.event.ConsumedEvent;

/**
 * Created by fangtao on 16/9/5.
 */
public interface ConsumedEventService {
    ConsumedEvent findOne(String id);

    ConsumedEvent create(String id);
}
