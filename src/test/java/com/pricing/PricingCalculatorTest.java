package com.pricing;

import com.pricing.model.*;
import com.pricing.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class PricingCalculatorTest {
    
    private PricingCalculator pricingCalculator;
    
    @BeforeEach
    void setUp() {
        pricingCalculator = new PricingCalculator();
    }
    
    @Test
    void testCalculatePriceWithRegularCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0, 50.0);
        List<Integer> quantities = Arrays.asList(2, 1);
        Order order = new Order(prices, quantities, CustomerType.REGULAR, null);
        
        PricingResult result = pricingCalculator.calculatePrice(order);
        
        assertEquals(250.0, result.getSubtotal(), 0.01);
        assertEquals(0.0, result.getDiscountAmount(), 0.01);
        assertEquals(20.0, result.getTax(), 0.01);
        assertEquals(270.0, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        Order order = new Order(prices, quantities, CustomerType.VIP, null);
        
        PricingResult result = pricingCalculator.calculatePrice(order);
        
        assertEquals(100.0, result.getSubtotal(), 0.01);
        assertEquals(5.0, result.getDiscountAmount(), 0.01);
        assertEquals(7.6, result.getTax(), 0.01);
        assertEquals(102.6, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithDiscountCode() {
        List<Double> prices = Arrays.asList(200.0);
        List<Integer> quantities = Arrays.asList(1);
        Order order = new Order(prices, quantities, CustomerType.REGULAR, DiscountCode.SAVE10);
        
        PricingResult result = pricingCalculator.calculatePrice(order);
        
        assertEquals(200.0, result.getSubtotal(), 0.01);
        assertEquals(20.0, result.getDiscountAmount(), 0.01);
        assertEquals(14.4, result.getTax(), 0.01);
        assertEquals(194.4, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerAndDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        Order order = new Order(prices, quantities, CustomerType.VIP, DiscountCode.SAVE20);
        
        PricingResult result = pricingCalculator.calculatePrice(order);
        
        assertEquals(100.0, result.getSubtotal(), 0.01);
        assertEquals(25.0, result.getDiscountAmount(), 0.01);
        assertEquals(6.0, result.getTax(), 0.01);
        assertEquals(81.0, result.getFinalPrice(), 0.01);
    }
}
