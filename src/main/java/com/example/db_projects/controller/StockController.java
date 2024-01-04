package com.example.db_projects.controller;

import com.example.db_projects.model.StockStatus;
import com.example.db_projects.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/status")
    public ResponseEntity<List<StockStatus>> getStockStatus() {
        List<StockStatus> stockStatuses = stockService.getStockStatus();
        return ResponseEntity.ok(stockStatuses);
    }
}
