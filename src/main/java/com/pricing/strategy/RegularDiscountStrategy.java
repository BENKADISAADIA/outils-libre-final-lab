package com.pricing.strategy;

import com.pricing.constants.PricingConstants;
import com.pricing.model.DiscountCode;

public class RegularDiscountStrategy implements DiscountStrategy {
    
    @Override
    public double calculateDiscount(double amount, DiscountCode discountCode) {
        if (discountCode == null) {
            return 0.0;
        }
        
        return switch (discountCode) {
            case SAVE10 -> amount * PricingConstants.SAVE10_DISCOUNT;
            case SAVE20 -> amount * PricingConstants.SAVE20_DISCOUNT;
            case SAVE30 -> amount * PricingConstants.SAVE30_DISCOUNT;
            case WELCOME -> amount * PricingConstants.WELCOME_DISCOUNT;
            case SPECIAL -> amount * PricingConstants.SPECIAL_DISCOUNT;
        };
    }
}
