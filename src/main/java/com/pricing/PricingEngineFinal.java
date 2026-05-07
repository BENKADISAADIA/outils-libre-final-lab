package com.pricing;

import com.pricing.constants.PricingConstants;
import com.pricing.factory.DiscountStrategyFactory;
import com.pricing.model.*;
import com.pricing.printer.InvoicePrinter;
import com.pricing.service.SubtotalCalculator;
import com.pricing.service.TaxCalculator;
import com.pricing.strategy.DiscountStrategy;

import java.util.UUID;

public class PricingEngineFinal {
    
    private final SubtotalCalculator subtotalCalculator;
    private final TaxCalculator taxCalculator;
    private final InvoicePrinter invoicePrinter;
    
    public PricingEngineFinal() {
        this.subtotalCalculator = new SubtotalCalculator();
        this.taxCalculator = new TaxCalculator();
        this.invoicePrinter = new InvoicePrinter();
    }
    
    public PricingEngineFinal(SubtotalCalculator subtotalCalculator, 
                             TaxCalculator taxCalculator, 
                             InvoicePrinter invoicePrinter) {
        this.subtotalCalculator = subtotalCalculator;
        this.taxCalculator = taxCalculator;
        this.invoicePrinter = invoicePrinter;
    }
    
    public PricingResult calculatePrice(PricingRequest request) {
        double subtotal = subtotalCalculator.calculateSubtotal(
            new Order(request.getPrices(), request.getQuantities(), request.getCustomerType(), request.getDiscountCode())
        );
        
        DiscountStrategy discountStrategy = DiscountStrategyFactory.createStrategy(request.getCustomerType());
        double discountAmount = discountStrategy.calculateDiscount(subtotal, request.getDiscountCode());
        
        double taxableAmount = subtotal - discountAmount;
        double tax = taxCalculator.calculateTax(taxableAmount);
        
        double finalPrice = subtotal - discountAmount + tax;
        
        return new PricingResult(subtotal, discountAmount, tax, finalPrice);
    }
    
    public Invoice createInvoice(PricingRequest request) {
        PricingResult pricingResult = calculatePrice(request);
        return new Invoice(request.getInvoiceNumber(), request.getCustomerType(), 
                          request.getDiscountCode(), pricingResult);
    }
    
    public void processAndPrintInvoice(PricingRequest request) {
        Invoice invoice = createInvoice(request);
        invoicePrinter.printInvoice(invoice);
    }
    
    public String getInvoiceAsString(PricingRequest request) {
        Invoice invoice = createInvoice(request);
        return invoicePrinter.getInvoiceAsString(invoice);
    }
}
