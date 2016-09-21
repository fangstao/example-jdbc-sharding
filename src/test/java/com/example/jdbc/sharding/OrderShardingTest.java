package com.example.jdbc.sharding;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.example.jdbc.sharding.domain.Order;
import com.example.jdbc.sharding.event.OrderCreatedEvent;
import com.example.jdbc.sharding.repository.OrderRepository;
import com.example.jdbc.sharding.repository.Sequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by fangtao on 16/8/21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderShardingTest {
    @Resource
    JdbcTemplate jdbcTemplate;
    @Resource
    Producer producer;
    @Resource
    TransactionProducer transactionProducer;
    @Resource
    OrderRepository orderRepository;
    @Resource
    Consumer consumer;
    @Resource
    FSTConfiguration fstConfiguration;

    @Test
    public void sharding() throws Exception {

        jdbcTemplate.queryForList("select * from t_order");
    }

    @Test
    public void insertOrder() throws Exception {
        jdbcTemplate.update("insert INTO t_order(order_id,user_id,status) VALUES (?,?,?)", 1, 1, "wait_payment");
    }

    @Test
    public void batchInsert() throws Exception {
        for (long i = 0; i < 10240; i++) {
            jdbcTemplate.update("insert INTO t_order(order_id,user_id,status) VALUES (?,?,?)", i, i % 100, "payment");
        }
    }

    @Test
    public void queryByUserId() throws Exception {
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("SELECT order_id, user_id, status FROM  t_order WHERE user_id = ?", 100);
        orders.forEach(p -> System.out.println(p.get("user_id") + "   " + p.get("order_id")));

    }

    @Test
    public void createOrder() throws Exception {
        long userId = 100L;
        Long orderId = Sequence.nextSequence();
        OrderCreatedEvent event = OrderCreatedEvent.create(orderId, userId);
        Message message = new Message();
        String msgid = UUID.randomUUID().toString();
        message.setMsgID(msgid);
        message.setTopic(OrderCreatedEvent.TOPIC);
        message.setBody(fstConfiguration.asByteArray(event));

        transactionProducer.send(message, new LocalTransactionExecuter() {
            @Override
            public TransactionStatus execute(Message msg, Object arg) {
                try {
                    Long orderId = event.getOrderId();

                    Order order = Order.create(orderId, userId, "payment");
                    orderRepository.save(order);
                    //System.exit(0);
                    //创建订单成功,提交信息
                    return TransactionStatus.CommitTransaction;
                } catch (RuntimeException e) { //订单创建失败,回滚消息
                    e.printStackTrace();
                    return TransactionStatus.RollbackTransaction;
                }
            }
        }, event);

    }


    @Test
    public void orderCreatedConsume() throws Exception {
        consumer.subscribe("ft-order-topic", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                byte[] body = message.getBody();
                Object o = fstConfiguration.asObject(body);
                System.out.println(o);
                return Action.CommitMessage;
            }
        });
        Thread.sleep(50000);
    }
}
