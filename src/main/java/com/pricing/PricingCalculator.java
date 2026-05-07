package com.pricing;

import com.pricing.model.*;
import com.pricing.service.*;

public class PricingCalculator {
    
    private final SubtotalCalculator subtotalCalculator;
    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;
    
    public PricingCalculator() {
        this.subtotalCalculator = new SubtotalCalculator();
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator = new TaxCalculator();
    }
    
    public PricingCalculator(SubtotalCalculator subtotalCalculator, 
                           DiscountCalculator discountCalculator, 
                           TaxCalculator taxCalculator) {
        this.subtotalCalculator = subtotalCalculator;
        this.discountCalculator = discountCalculator;
        this.taxCalculator = taxCalculator;
    }
    
    public PricingResult calculatePrice(Order order) {
        double subtotal = subtotalCalculator.calculateSubtotal(order);
        
        double discountRate = discountCalculator.calculateDiscountRate(
            order.getDiscountCode(), order.getCustomerType());
        double discountAmount = discountCalculator.calculateDiscountAmount(subtotal, discountRate);
        
        double taxableAmount = subtotal - discountAmount;
        double tax = taxCalculator.calculateTax(taxableAmount);
        
        double finalPrice = subtotal - discountAmount + tax;
        
        return new PricingResult(subtotal, discountAmount, tax, finalPrice);
    }
}
