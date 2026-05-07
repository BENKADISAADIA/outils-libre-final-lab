package com.pricing.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private final List<Double> prices;
    private final List<Integer> quantities;
    private final CustomerType customerType;
    private final DiscountCode discountCode;
    
    public Order(List<Double> prices, List<Integer> quantities, CustomerType customerType, DiscountCode discountCode) {
        this.prices = Objects.requireNonNull(prices, "Prices cannot be null");
        this.quantities = Objects.requireNonNull(quantities, "Quantities cannot be null");
        this.customerType = Objects.requireNonNull(customerType, "Customer type cannot be null");
        this.discountCode = discountCode; // Can be null
    }
    
    public List<Double> getPrices() {
        return prices;
    }
    
    public List<Integer> getQuantities() {
        return quantities;
    }
    
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public DiscountCode getDiscountCode() {
        return discountCode;
    }
}
