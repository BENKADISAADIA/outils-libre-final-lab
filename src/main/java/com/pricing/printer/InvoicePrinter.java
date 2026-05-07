package com.pricing.printer;

import com.pricing.model.Invoice;

public class InvoicePrinter {
    
    public void printInvoice(Invoice invoice) {
        System.out.println("=================================");
        System.out.println("INVOICE");
        System.out.println("=================================");
        System.out.println("Invoice Number: " + invoice.getInvoiceNumber());
        System.out.println("Date: " + invoice.getFormattedDate());
        System.out.println("Customer Type: " + invoice.getCustomerType());
        System.out.println("Discount Code: " + (invoice.getDiscountCode() != null ? invoice.getDiscountCode() : "None"));
        System.out.println("---------------------------------");
        System.out.println("Subtotal: $" + String.format("%.2f", invoice.getPricingResult().getSubtotal()));
        System.out.println("Discount: -$" + String.format("%.2f", invoice.getPricingResult().getDiscountAmount()));
        System.out.println("Tax: $" + String.format("%.2f", invoice.getPricingResult().getTax()));
        System.out.println("---------------------------------");
        System.out.println("TOTAL: $" + String.format("%.2f", invoice.computeTotal()));
        System.out.println("=================================");
    }
    
    public String getInvoiceAsString(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("=================================\n");
        sb.append("INVOICE\n");
        sb.append("=================================\n");
        sb.append("Invoice Number: ").append(invoice.getInvoiceNumber()).append("\n");
        sb.append("Date: ").append(invoice.getFormattedDate()).append("\n");
        sb.append("Customer Type: ").append(invoice.getCustomerType()).append("\n");
        sb.append("Discount Code: ").append(invoice.getDiscountCode() != null ? invoice.getDiscountCode() : "None").append("\n");
        sb.append("---------------------------------\n");
        sb.append("Subtotal: $").append(String.format("%.2f", invoice.getPricingResult().getSubtotal())).append("\n");
        sb.append("Discount: -$").append(String.format("%.2f", invoice.getPricingResult().getDiscountAmount())).append("\n");
        sb.append("Tax: $").append(String.format("%.2f", invoice.getPricingResult().getTax())).append("\n");
        sb.append("---------------------------------\n");
        sb.append("TOTAL: $").append(String.format("%.2f", invoice.computeTotal())).append("\n");
        sb.append("=================================\n");
        return sb.toString();
    }
}
