package com.example.jdbc.sharding.repository;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fangtao on 16/9/11.
 */
public class Sequence {
    private static AtomicLong sequence;

    static {
        sequence = new AtomicLong(new Date().getTime() / 1000);
    }

    public static Long nextSequence() {
        return sequence.incrementAndGet();
    }
}
