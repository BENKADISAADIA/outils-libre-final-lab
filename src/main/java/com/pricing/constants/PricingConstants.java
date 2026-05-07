package com.pricing.constants;

public final class PricingConstants {
    
    // Discount rates
    public static final double SAVE10_DISCOUNT = 0.10;
    public static final double SAVE20_DISCOUNT = 0.20;
    public static final double SAVE30_DISCOUNT = 0.30;
    public static final double WELCOME_DISCOUNT = 0.15;
    public static final double SPECIAL_DISCOUNT = 0.25;
    public static final double VIP_EXTRA_DISCOUNT = 0.05;
    
    // Tax rate
    public static final double TAX_RATE = 0.08;
    
    // VIP discount rate
    public static final double VIP_DISCOUNT_RATE = 0.95;
    
    // Private constructor to prevent instantiation
    private PricingConstants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}
