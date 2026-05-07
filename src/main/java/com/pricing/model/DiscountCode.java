package com.pricing.model;

public enum DiscountCode {
    SAVE10(0.10),
    SAVE20(0.20),
    SAVE30(0.30),
    WELCOME(0.15),
    SPECIAL(0.25);
    
    private final double discountRate;
    
    DiscountCode(double discountRate) {
        this.discountRate = discountRate;
    }
    
    public double getDiscountRate() {
        return discountRate;
    }
    
    public static DiscountCode fromString(String code) {
        if (code == null) {
            return null;
        }
        try {
            return DiscountCode.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
