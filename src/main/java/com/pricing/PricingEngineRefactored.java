package com.pricing;

import com.pricing.model.*;
import com.pricing.service.*;
import java.util.List;
import java.util.Arrays;

public class PricingEngineRefactored {
    
    private final PricingCalculator pricingCalculator;
    
    public PricingEngineRefactored() {
        this.pricingCalculator = new PricingCalculator();
    }
    
    public PricingEngineRefactored(PricingCalculator pricingCalculator) {
        this.pricingCalculator = pricingCalculator;
    }
    
    public PricingResult calculatePrice(List<Double> prices, List<Integer> quantities, 
                                      CustomerType customerType, String discountCode) {
        DiscountCode code = DiscountCode.fromString(discountCode);
        Order order = new Order(prices, quantities, customerType, code);
        return pricingCalculator.calculatePrice(order);
    }
    
    public void processAndPrint(List<Double> prices, List<Integer> quantities, 
                              CustomerType customerType, String discountCode) {
        PricingResult result = calculatePrice(prices, quantities, customerType, discountCode);
        System.out.println("Order processed:");
        System.out.println(result.toString());
    }
    
    public boolean isValidDiscountCode(String code) {
        return DiscountCode.fromString(code) != null;
    }
    
    public double applyCustomerTypeDiscount(double amount, CustomerType customerType) {
        if (customerType == CustomerType.VIP) {
            return amount * 0.95; // 5% discount for VIP
        }
        return amount; // No discount for regular customers
    }
}
