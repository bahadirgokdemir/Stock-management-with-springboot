package com.example.db_projects.dto;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Date;

public class OrderDto {
    private Long productId;
    private Integer quantity;
    private Double pricePerUnit;
    private String deliveryCompany;

    private Double productPrice;
    private Double total_revenue;
    private String category;
    private Boolean status;
    private String productName;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private Long orderId;

    public Double getTotalRevenue() {
        if (this.quantity != null && this.pricePerUnit != null) {
            return this.quantity * this.pricePerUnit;
        }
        return null;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(Double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    private Date deliveryDate;

    @Override
    public String toString() {
        return "OrderDto{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", deliveryCompany='" + deliveryCompany + '\'' +
                ", category='" + category + '\'' +
                ", status=" + status +
                ", orderId=" + orderId +
                ", deliveryDate=" + deliveryDate +
                '}';
    }


    // Getters and Setters
}

