package com.pricing.service;

import com.pricing.model.CustomerType;
import com.pricing.model.DiscountCode;

public class DiscountCalculator {
    
    private static final double VIP_EXTRA_DISCOUNT = 0.05;
    
    public double calculateDiscountRate(DiscountCode discountCode, CustomerType customerType) {
        double discountRate = 0.0;
        
        if (discountCode != null) {
            discountRate = discountCode.getDiscountRate();
        }
        
        if (customerType == CustomerType.VIP) {
            discountRate += VIP_EXTRA_DISCOUNT;
        }
        
        return discountRate;
    }
    
    public double calculateDiscountAmount(double subtotal, double discountRate) {
        return subtotal * discountRate;
    }
}
