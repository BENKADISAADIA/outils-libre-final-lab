package com.pricing.service;

public class TaxCalculator {
    
    private static final double TAX_RATE = 0.08;
    
    public double calculateTax(double taxableAmount) {
        return taxableAmount * TAX_RATE;
    }
    
    public double getTaxRate() {
        return TAX_RATE;
    }
}
