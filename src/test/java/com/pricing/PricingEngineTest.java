package com.pricing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class PricingEngineTest {
    
    private PricingEngine pricingEngine;
    
    @BeforeEach
    void setUp() {
        pricingEngine = new PricingEngine();
    }
    
    @Test
    void testCalculatePriceWithRegularCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0, 50.0);
        List<Integer> quantities = Arrays.asList(2, 1);
        PricingEngine.Order order = new PricingEngine.Order(prices, quantities, "REGULAR", null);
        
        PricingEngine.PricingResult result = pricingEngine.calculatePrice(order);
        
        assertEquals(250.0, result.subtotal, 0.01);
        assertEquals(0.0, result.discountAmount, 0.01);
        assertEquals(20.0, result.tax, 0.01); // 8% of 250
        assertEquals(270.0, result.finalPrice, 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        PricingEngine.Order order = new PricingEngine.Order(prices, quantities, "VIP", null);
        
        PricingEngine.PricingResult result = pricingEngine.calculatePrice(order);
        
        assertEquals(100.0, result.subtotal, 0.01);
        assertEquals(5.0, result.discountAmount, 0.01); // VIP gets 5% discount
        assertEquals(7.6, result.tax, 0.01); // 8% of 95
        assertEquals(102.6, result.finalPrice, 0.01);
    }
    
    @Test
    void testCalculatePriceWithDiscountCode() {
        List<Double> prices = Arrays.asList(200.0);
        List<Integer> quantities = Arrays.asList(1);
        PricingEngine.Order order = new PricingEngine.Order(prices, quantities, "REGULAR", "SAVE10");
        
        PricingEngine.PricingResult result = pricingEngine.calculatePrice(order);
        
        assertEquals(200.0, result.subtotal, 0.01);
        assertEquals(20.0, result.discountAmount, 0.01); // 10% discount
        assertEquals(14.4, result.tax, 0.01); // 8% of 180
        assertEquals(194.4, result.finalPrice, 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerAndDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        PricingEngine.Order order = new PricingEngine.Order(prices, quantities, "VIP", "SAVE20");
        
        PricingEngine.PricingResult result = pricingEngine.calculatePrice(order);
        
        assertEquals(100.0, result.subtotal, 0.01);
        assertEquals(25.0, result.discountAmount, 0.01); // 20% + 5% VIP = 25%
        assertEquals(6.0, result.tax, 0.01); // 8% of 75
        assertEquals(81.0, result.finalPrice, 0.01);
    }
    
    @Test
    void testCalculatePriceWithMultipleItems() {
        List<Double> prices = Arrays.asList(10.0, 20.0, 30.0);
        List<Integer> quantities = Arrays.asList(1, 2, 3);
        PricingEngine.Order order = new PricingEngine.Order(prices, quantities, "REGULAR", "SAVE30");
        
        PricingEngine.PricingResult result = pricingEngine.calculatePrice(order);
        
        assertEquals(140.0, result.subtotal, 0.01); // 10*1 + 20*2 + 30*3
        assertEquals(42.0, result.discountAmount, 0.01); // 30% of 140
        assertEquals(7.84, result.tax, 0.01); // 8% of 98
        assertEquals(105.84, result.finalPrice, 0.01);
    }
    
    @Test
    void testIsValidDiscount() {
        assertTrue(pricingEngine.isValidDiscount("SAVE10"));
        assertTrue(pricingEngine.isValidDiscount("SAVE20"));
        assertTrue(pricingEngine.isValidDiscount("SAVE30"));
        assertTrue(pricingEngine.isValidDiscount("WELCOME"));
        assertTrue(pricingEngine.isValidDiscount("SPECIAL"));
        assertFalse(pricingEngine.isValidDiscount("INVALID"));
        assertFalse(pricingEngine.isValidDiscount(null));
    }
    
    @Test
    void testDoStuff() {
        assertEquals(95.0, pricingEngine.doStuff(100.0, "VIP"), 0.01);
        assertEquals(100.0, pricingEngine.doStuff(100.0, "REGULAR"), 0.01);
        assertEquals(100.0, pricingEngine.doStuff(100.0, "UNKNOWN"), 0.01);
    }
    
    @Test
    void testPricingResultToString() {
        PricingEngine.PricingResult result = new PricingEngine.PricingResult(100.0, 10.0, 7.2, 97.2);
        String expected = "Subtotal: $100.00, Discount: $10.00, Tax: $7.20, Final: $97.20";
        assertEquals(expected, result.toString());
    }
    
    @Test
    void testEdgeCases() {
        // Test with empty lists
        PricingEngine.Order emptyOrder = new PricingEngine.Order(Arrays.asList(), Arrays.asList(), "REGULAR", null);
        PricingEngine.PricingResult emptyResult = pricingEngine.calculatePrice(emptyOrder);
        assertEquals(0.0, emptyResult.subtotal, 0.01);
        assertEquals(0.0, emptyResult.finalPrice, 0.01);
        
        // Test with mismatched list sizes
        PricingEngine.Order mismatchOrder = new PricingEngine.Order(Arrays.asList(100.0, 200.0), Arrays.asList(1), "REGULAR", null);
        PricingEngine.PricingResult mismatchResult = pricingEngine.calculatePrice(mismatchOrder);
        assertEquals(100.0, mismatchResult.subtotal, 0.01); // Only first item processed
    }
}
