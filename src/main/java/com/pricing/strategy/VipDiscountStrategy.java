package com.pricing.strategy;

import com.pricing.constants.PricingConstants;
import com.pricing.model.DiscountCode;

public class VipDiscountStrategy implements DiscountStrategy {
    
    @Override
    public double calculateDiscount(double amount, DiscountCode discountCode) {
        double baseDiscount = 0.0;
        
        if (discountCode != null) {
            baseDiscount = switch (discountCode) {
                case SAVE10 -> amount * PricingConstants.SAVE10_DISCOUNT;
                case SAVE20 -> amount * PricingConstants.SAVE20_DISCOUNT;
                case SAVE30 -> amount * PricingConstants.SAVE30_DISCOUNT;
                case WELCOME -> amount * PricingConstants.WELCOME_DISCOUNT;
                case SPECIAL -> amount * PricingConstants.SPECIAL_DISCOUNT;
            };
        }
        
        // Add VIP extra discount
        double vipExtraDiscount = amount * PricingConstants.VIP_EXTRA_DISCOUNT;
        return baseDiscount + vipExtraDiscount;
    }
}
