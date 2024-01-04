package com.example.db_projects.service;

import com.example.db_projects.model.StockStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<StockStatus> getStockStatus() {
        String sql = "SELECT c.id, c.name, COALESCE(SUM(p.stock_quantity), 0) as totalStock, "
                + "COALESCE(AVG(p.stock_quantity), 0) as stockFillRate "
                + "FROM categories c "
                + "LEFT JOIN products p ON c.id = p.category_id "
                + "GROUP BY c.id, c.name";
        return jdbcTemplate.query(sql, new StockStatusRowMapper());
    }

    private static class StockStatusRowMapper implements RowMapper<StockStatus> {
        @Override
        public StockStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            StockStatus stockStatus = new StockStatus();
            stockStatus.setCategoryId(rs.getLong("id"));
            stockStatus.setCategoryName(rs.getString("name"));
            stockStatus.setTotalStock(rs.getInt("totalStock"));
            stockStatus.setStockFillRate(rs.getDouble("stockFillRate"));
            return stockStatus;
        }
    }
}
