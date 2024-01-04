package com.example.db_projects.service;

import com.example.db_projects.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public void createOrder(OrderDto orderDto) throws Exception {
        try {
            String productSql = "SELECT name, category_id, price, stock_quantity FROM products WHERE id = ?";
            logger.info("Ürün bilgisi çekiliyor: productId=" + orderDto.getProductId());
            Map<String, Object> product = jdbcTemplate.queryForMap(productSql, orderDto.getProductId());
            logger.info("Ürün bilgileri: " + product);

            String categoryName = product.get("name") + "-" + product.get("category_id");
            BigDecimal productPriceBD = (BigDecimal) product.get("price");
            double productPrice = productPriceBD.doubleValue();
            double totalIncome = orderDto.getQuantity() * productPrice;

            orderDto.setCategory(categoryName);

            int stockQuantity = (int) product.get("stock_quantity");
            if (orderDto.getQuantity() > stockQuantity) {
                throw new Exception("Yetersiz stok miktarı");
            }

            int newStockQuantity = stockQuantity - orderDto.getQuantity();
            String updateStockSql = "UPDATE products SET stock_quantity = ? WHERE id = ?";
            jdbcTemplate.update(updateStockSql, newStockQuantity, orderDto.getProductId());

            logger.info("Sipariş bilgileri: Ürün Adı: " + categoryName + ", Toplam Gelir: " + totalIncome);

            String orderSql = "INSERT INTO order_details (product_id, quantity, price_per_unit, delivery_company, delivery_date, category, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(orderSql, orderDto.getProductId(), orderDto.getQuantity(), orderDto.getPricePerUnit(), orderDto.getDeliveryCompany(), orderDto.getDeliveryDate(), categoryName, true); // son parametre olarak status eklenir

            logger.info("Sipariş veritabanına eklendi: " + orderDto);
        }
        catch (Exception e) {
            logger.error("Sipariş oluşturma hatası: ", e);
            throw e;
        }
    }

    public List<OrderDto> getAllOrders() {
        try {
            String sql = "SELECT od.id, od.*, p.name AS productName, p.price AS productPrice FROM order_details od JOIN products p ON od.product_id = p.id WHERE od.status = true";
            List<OrderDto> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDto.class));
            logger.info("Alınan siparişler: " + orders);
            return orders;
        } catch (Exception e) {
            logger.error("Siparişleri alırken hata oluştu: ", e);
            throw e;
        }
    }



    public void updateOrderStatus(int orderId) {
        String sql = "UPDATE order_details SET status = false WHERE id = ?";
        jdbcTemplate.update(sql, orderId);
    }
    public List<OrderDto> getDeliveredOrders() {
        String sql = "SELECT od.*, p.name AS productName FROM order_details od JOIN products p ON od.product_id = p.id WHERE od.status = false";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDto.class));
    }





}

