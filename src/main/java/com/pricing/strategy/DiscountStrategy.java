package com.pricing.strategy;

import com.pricing.model.DiscountCode;

public interface DiscountStrategy {
    double calculateDiscount(double amount, DiscountCode discountCode);
}
