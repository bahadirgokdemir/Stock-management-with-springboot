package com.example.db_projects.service;

import com.example.db_projects.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> getAllProducts() {
        String sql = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(Long id) {
        String sql = "SELECT p.*, c.name as category_name FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
    }

    public Product addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, stock_quantity, category_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getStockQuantity(), product.getCategoryId());

        updateCategoryStock(product.getCategoryId());

        return product;
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock_quantity = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getStockQuantity(), product.getCategoryId(), product.getId());
        updateCategoryStock(product.getCategoryId());
    }



    public void deleteProduct(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM order_details WHERE product_id = ?",
                new Object[]{id}, Integer.class);

        if (count != null && count > 0) {
            throw new DataIntegrityViolationException("Ürün sipariş detaylarında kullanıldığı için silinemez.");
        }

        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setStockQuantity(rs.getInt("stock_quantity"));
            String categoryName = rs.getString("category_name");
            System.out.println("Category Name: " + categoryName);
            product.setCategoryName(categoryName);
            return product;
        }
    }

    private void updateCategoryStock(Long categoryId) {
        String sqlUpdateStock = "UPDATE categories SET total_stock = (SELECT SUM(stock_quantity) FROM products WHERE category_id = ?) WHERE id = ?";
        jdbcTemplate.update(sqlUpdateStock, categoryId, categoryId);
    }

}
