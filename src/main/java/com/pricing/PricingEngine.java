package com.pricing;

import java.util.List;
import java.util.ArrayList;

public class PricingEngine {
    
    public static class Order {
        public List<Double> prices;
        public List<Integer> quantities;
        public String customerType;
        public String discountCode;
        
        public Order(List<Double> prices, List<Integer> quantities, String customerType, String discountCode) {
            this.prices = prices;
            this.quantities = quantities;
            this.customerType = customerType;
            this.discountCode = discountCode;
        }
    }
    
    public static class PricingResult {
        public double subtotal;
        public double discountAmount;
        public double tax;
        public double finalPrice;
        
        public PricingResult(double subtotal, double discountAmount, double tax, double finalPrice) {
            this.subtotal = subtotal;
            this.discountAmount = discountAmount;
            this.tax = tax;
            this.finalPrice = finalPrice;
        }
        
        @Override
        public String toString() {
            return String.format("Subtotal: $%.2f, Discount: $%.2f, Tax: $%.2f, Final: $%.2f", 
                               subtotal, discountAmount, tax, finalPrice);
        }
    }
    
    public PricingResult calculatePrice(Order order) {
        // Calculate subtotal - this is messy and hard to read
        double subtotal = 0.0;
        if (order.prices != null && order.quantities != null) {
            for (int i = 0; i < order.prices.size(); i++) {
                if (i < order.quantities.size()) {
                    subtotal += order.prices.get(i) * order.quantities.get(i);
                }
            }
        }
        
        // Calculate discount - lots of magic numbers and repeated code
        double discountRate = 0.0;
        if (order.discountCode != null) {
            if (order.discountCode.equals("SAVE10")) {
                discountRate = 0.1;
            } else if (order.discountCode.equals("SAVE20")) {
                discountRate = 0.2;
            } else if (order.discountCode.equals("SAVE30")) {
                discountRate = 0.3;
            } else if (order.discountCode.equals("WELCOME")) {
                discountRate = 0.15;
            } else if (order.discountCode.equals("SPECIAL")) {
                discountRate = 0.25;
            }
        }
        
        // VIP customers get extra discount
        if (order.customerType != null && order.customerType.equals("VIP")) {
            discountRate += 0.05;
        }
        
        double discountAmount = subtotal * discountRate;
        
        // Calculate tax - magic number again
        double taxRate = 0.08;
        double taxableAmount = subtotal - discountAmount;
        double tax = taxableAmount * taxRate;
        
        // Calculate final price
        double finalPrice = subtotal - discountAmount + tax;
        
        // This method is doing too many things!
        return new PricingResult(subtotal, discountAmount, tax, finalPrice);
    }
    
    // This method has poor naming and does multiple things
    public void processAndPrint(List<Double> prices, List<Integer> quantities, String customerType, String discountCode) {
        Order order = new Order(prices, quantities, customerType, discountCode);
        PricingResult result = calculatePrice(order);
        System.out.println("Order processed:");
        System.out.println(result.toString());
    }
    
    // Another poorly named method that mixes concerns
    public boolean isValidDiscount(String code) {
        if (code == null) return false;
        if (code.equals("SAVE10") || code.equals("SAVE20") || code.equals("SAVE30") || 
            code.equals("WELCOME") || code.equals("SPECIAL")) {
            return true;
        }
        return false;
    }
    
    // This method has no clear purpose and poor naming
    public double doStuff(double amount, String type) {
        if (type.equals("VIP")) {
            return amount * 0.95;
        } else if (type.equals("REGULAR")) {
            return amount;
        }
        return amount;
    }
}
