package com.pricing;

import com.pricing.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class PricingEngineRefactoredTest {
    
    private PricingEngineRefactored pricingEngine;
    
    @BeforeEach
    void setUp() {
        pricingEngine = new PricingEngineRefactored();
    }
    
    @Test
    void testCalculatePriceWithRegularCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0, 50.0);
        List<Integer> quantities = Arrays.asList(2, 1);
        
        PricingResult result = pricingEngine.calculatePrice(prices, quantities, CustomerType.REGULAR, null);
        
        assertEquals(250.0, result.getSubtotal(), 0.01);
        assertEquals(0.0, result.getDiscountAmount(), 0.01);
        assertEquals(20.0, result.getTax(), 0.01);
        assertEquals(270.0, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerNoDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        
        PricingResult result = pricingEngine.calculatePrice(prices, quantities, CustomerType.VIP, null);
        
        assertEquals(100.0, result.getSubtotal(), 0.01);
        assertEquals(5.0, result.getDiscountAmount(), 0.01);
        assertEquals(7.6, result.getTax(), 0.01);
        assertEquals(102.6, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithDiscountCode() {
        List<Double> prices = Arrays.asList(200.0);
        List<Integer> quantities = Arrays.asList(1);
        
        PricingResult result = pricingEngine.calculatePrice(prices, quantities, CustomerType.REGULAR, "SAVE10");
        
        assertEquals(200.0, result.getSubtotal(), 0.01);
        assertEquals(20.0, result.getDiscountAmount(), 0.01);
        assertEquals(14.4, result.getTax(), 0.01);
        assertEquals(194.4, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testCalculatePriceWithVIPCustomerAndDiscount() {
        List<Double> prices = Arrays.asList(100.0);
        List<Integer> quantities = Arrays.asList(1);
        
        PricingResult result = pricingEngine.calculatePrice(prices, quantities, CustomerType.VIP, "SAVE20");
        
        assertEquals(100.0, result.getSubtotal(), 0.01);
        assertEquals(25.0, result.getDiscountAmount(), 0.01);
        assertEquals(6.0, result.getTax(), 0.01);
        assertEquals(81.0, result.getFinalPrice(), 0.01);
    }
    
    @Test
    void testIsValidDiscountCode() {
        assertTrue(pricingEngine.isValidDiscountCode("SAVE10"));
        assertTrue(pricingEngine.isValidDiscountCode("SAVE20"));
        assertTrue(pricingEngine.isValidDiscountCode("SAVE30"));
        assertTrue(pricingEngine.isValidDiscountCode("WELCOME"));
        assertTrue(pricingEngine.isValidDiscountCode("SPECIAL"));
        assertFalse(pricingEngine.isValidDiscountCode("INVALID"));
        assertFalse(pricingEngine.isValidDiscountCode(null));
    }
    
    @Test
    void testApplyCustomerTypeDiscount() {
        assertEquals(95.0, pricingEngine.applyCustomerTypeDiscount(100.0, CustomerType.VIP), 0.01);
        assertEquals(100.0, pricingEngine.applyCustomerTypeDiscount(100.0, CustomerType.REGULAR), 0.01);
    }
}
