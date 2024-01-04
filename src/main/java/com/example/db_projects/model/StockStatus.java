package com.example.db_projects.model;

public class StockStatus {
    private Long categoryId;
    private String categoryName;
    private int totalStock;
    private double stockFillRate;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public double getStockFillRate() {
        return stockFillRate;
    }

    public void setStockFillRate(double stockFillRate) {
        this.stockFillRate = stockFillRate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
