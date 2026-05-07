package com.pricing.model;

import java.util.List;
import java.util.Objects;

public class PricingRequest {
    private final List<Double> prices;
    private final List<Integer> quantities;
    private final CustomerType customerType;
    private final DiscountCode discountCode;
    private final String invoiceNumber;
    
    private PricingRequest(Builder builder) {
        this.prices = Objects.requireNonNull(builder.prices, "Prices cannot be null");
        this.quantities = Objects.requireNonNull(builder.quantities, "Quantities cannot be null");
        this.customerType = Objects.requireNonNull(builder.customerType, "Customer type cannot be null");
        this.discountCode = builder.discountCode;
        this.invoiceNumber = builder.invoiceNumber;
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
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private List<Double> prices;
        private List<Integer> quantities;
        private CustomerType customerType;
        private DiscountCode discountCode;
        private String invoiceNumber;
        
        public Builder prices(List<Double> prices) {
            this.prices = prices;
            return this;
        }
        
        public Builder quantities(List<Integer> quantities) {
            this.quantities = quantities;
            return this;
        }
        
        public Builder customerType(CustomerType customerType) {
            this.customerType = customerType;
            return this;
        }
        
        public Builder discountCode(DiscountCode discountCode) {
            this.discountCode = discountCode;
            return this;
        }
        
        public Builder discountCode(String discountCodeString) {
            this.discountCode = DiscountCode.fromString(discountCodeString);
            return this;
        }
        
        public Builder invoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }
        
        public PricingRequest build() {
            if (invoiceNumber == null) {
                invoiceNumber = "INV-" + System.currentTimeMillis();
            }
            return new PricingRequest(this);
        }
    }
}
