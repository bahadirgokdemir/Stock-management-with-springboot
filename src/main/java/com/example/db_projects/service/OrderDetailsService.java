package com.example.db_projects.service;

import com.example.db_projects.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class OrderDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<OrderDetails> getAllOrderDetails() {
        return jdbcTemplate.query("SELECT * FROM order_details", new BeanPropertyRowMapper<>(OrderDetails.class));
    }

    public OrderDetails getOrderDetailById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM order_details WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(OrderDetails.class));
    }

    public void addOrderDetail(OrderDetails orderDetail) {
        String detailSql = "INSERT INTO order_details (order_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(detailSql, orderDetail.getOrderId(), orderDetail.getProductId(), orderDetail.getQuantity(), orderDetail.getPricePerUnit());
        updateProductStock(orderDetail.getProductId(), orderDetail.getQuantity());
    }

    private void updateProductStock(Long productId, int quantity) {
        String stockSql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ?";
        jdbcTemplate.update(stockSql, quantity, productId);
    }

    public void updateOrderDetail(Long id, OrderDetails orderDetails) {
        String sql = "UPDATE order_details SET order_id = ?, product_id = ?, quantity = ?, price_per_unit = ? WHERE id = ?";
        jdbcTemplate.update(sql, orderDetails.getOrderId(), orderDetails.getProductId(), orderDetails.getQuantity(), orderDetails.getPricePerUnit(), id);
    }


    public void deleteOrderDetail(Long id) {
        jdbcTemplate.update("DELETE FROM order_details WHERE id = ?", id);
    }
}

