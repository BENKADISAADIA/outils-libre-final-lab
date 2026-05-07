package com.pricing.service;

import com.pricing.model.Order;
import java.util.List;

public class SubtotalCalculator {
    
    public double calculateSubtotal(Order order) {
        List<Double> prices = order.getPrices();
        List<Integer> quantities = order.getQuantities();
        
        double subtotal = 0.0;
        int minSize = Math.min(prices.size(), quantities.size());
        
        for (int i = 0; i < minSize; i++) {
            subtotal += prices.get(i) * quantities.get(i);
        }
        
        return subtotal;
    }
}
