package com.pricing.factory;

import com.pricing.model.CustomerType;
import com.pricing.strategy.DiscountStrategy;
import com.pricing.strategy.RegularDiscountStrategy;
import com.pricing.strategy.VipDiscountStrategy;

public class DiscountStrategyFactory {
    
    public static DiscountStrategy createStrategy(CustomerType customerType) {
        return switch (customerType) {
            case REGULAR -> new RegularDiscountStrategy();
            case VIP -> new VipDiscountStrategy();
        };
    }
}
