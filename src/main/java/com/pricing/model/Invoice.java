package com.pricing.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private final String invoiceNumber;
    private final LocalDateTime createdAt;
    private final CustomerType customerType;
    private final DiscountCode discountCode;
    private final PricingResult pricingResult;
    
    public Invoice(String invoiceNumber, CustomerType customerType, DiscountCode discountCode, PricingResult pricingResult) {
        this.invoiceNumber = invoiceNumber;
        this.createdAt = LocalDateTime.now();
        this.customerType = customerType;
        this.discountCode = discountCode;
        this.pricingResult = pricingResult;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public DiscountCode getDiscountCode() {
        return discountCode;
    }
    
    public PricingResult getPricingResult() {
        return pricingResult;
    }
    
    public String getFormattedDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public double computeTotal() {
        return pricingResult.getFinalPrice();
    }
}
