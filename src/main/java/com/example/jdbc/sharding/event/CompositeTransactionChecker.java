package com.example.jdbc.sharding.event;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by fangtao on 16/9/11.
 */
public class CompositeTransactionChecker implements LocalTransactionChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeTransactionChecker.class);


    private List<MessageChecker> messageCheckers;

    private FSTConfiguration fstConfiguration;

    @Override
    public TransactionStatus check(Message msg) {
        byte[] body = msg.getBody();
        EventBase event = (EventBase) fstConfiguration.asObject(body);
        for (MessageChecker checker : messageCheckers) {
            if (checker.accept(event)) {
                return checker.check(event);
            }
        }
        LOGGER.error("unknown event {}, can not find checker to check this event.", event);
        return TransactionStatus.Unknow;
    }

    public CompositeTransactionChecker(List<MessageChecker> messageCheckers, FSTConfiguration fstConfiguration) {
        this.messageCheckers = messageCheckers;
        this.fstConfiguration = fstConfiguration;
    }
}
