package com.example.jdbc.sharding.repository;


import com.example.jdbc.sharding.domain.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fangtao on 16/9/11.
 */
@Repository
public class OrderRepository {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Order save(Order order) {
        jdbcTemplate.update("insert INTO t_order(order_id, user_id, status) VALUES (?, ?, ?)", order.getId(), order.getUserId(), order.getStatus());
        return order;
    }

    /**
     * 根据订单ID查询订单,建议使用 {@link OrderRepository#findOne(Long, Long)},效率更高
     *
     * @param orderId 订单ID
     * @return
     */
    public Order findOne(Long orderId) {
        return
                jdbcTemplate.queryForObject("select order_id, user_id, status from t_order where order_id = ?", new OrderMapper(), orderId);

    }

    /**
     * 根据订单ID,用户ID查询订单
     *
     * @param orderId 订单ID
     * @param userId  用户ID,用来定位当前订单所在的数据库与所在表,加快查询速度
     * @return 订单
     */
    public Order findOne(Long orderId, Long userId) {
        return
                jdbcTemplate.queryForObject("select order_id, user_id, status from t_order where order_id = ? and user_id = ?", new OrderMapper(), orderId, userId);

    }

    class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            long order_id = resultSet.getLong("order_id");
            long user_id = resultSet.getLong("user_id");
            String status = resultSet.getString("status");
            return Order.create(order_id, user_id, status);
        }

    }
}
