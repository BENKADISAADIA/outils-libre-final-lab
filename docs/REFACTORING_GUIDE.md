# Comprehensive Refactoring Guide

## Overview
This guide demonstrates the step-by-step refactoring process from a poorly-designed monolithic class to a well-structured, maintainable codebase using design patterns.

## Initial Problems with PricingEngine

### Code Smells Identified:
1. **Long Method**: `calculatePrice()` method doing too many things
2. **Magic Numbers**: Hard-coded discount rates and tax values
3. **Poor Naming**: Methods like `doStuff()` with unclear purpose
4. **Mixed Concerns**: Calculation, validation, and printing in same class
5. **Violation of SRP**: Single class handling multiple responsibilities
6. **Tight Coupling**: Hard to test individual components

## Refactoring Steps

### Step 1: Extract Constants
**Problem**: Magic numbers scattered throughout code
**Solution**: Create `PricingConstants` class
```java
public final class PricingConstants {
    public static final double SAVE10_DISCOUNT = 0.10;
    public static final double SAVE20_DISCOUNT = 0.20;
    public static final double TAX_RATE = 0.08;
    // ...
}
```

### Step 2: Introduce Strategy Pattern
**Problem**: Discount logic embedded in main class
**Solution**: Create `DiscountStrategy` interface and implementations

#### Benefits:
- Open/Closed Principle: Easy to add new discount types
- Single Responsibility: Each strategy handles one discount type
- Testability: Can test each strategy independently

### Step 3: Add Factory Pattern
**Problem**: Direct instantiation of strategies
**Solution**: `DiscountStrategyFactory` for proper object creation

#### Benefits:
- Centralized object creation logic
- Easy to modify strategy selection
- Hides implementation details

### Step 4: Extract Service Classes
**Problem**: Multiple calculations in single method
**Solution**: Separate service classes for each calculation

#### Services Created:
- `SubtotalCalculator`: Handles price × quantity calculations
- `TaxCalculator`: Manages tax calculations
- `DiscountCalculator`: Applies discount logic

### Step 5: Introduce Parameter Object Pattern
**Problem**: Too many method parameters
**Solution**: `PricingRequest` with Builder pattern

#### Benefits:
- Cleaner method signatures
- Immutable request objects
- Easy to extend with new fields
- Built-in validation

### Step 6: Add Domain Models
**Problem**: Using primitive types and strings
**Solution**: Proper domain models with enums

#### Models Created:
- `CustomerType`: Enum for customer types
- `DiscountCode`: Enum with discount rates
- `Order`: Encapsulates order data
- `Invoice`: Complete invoice representation

### Step 7: Separate Presentation Logic
**Problem**: Printing mixed with business logic
**Solution**: `InvoicePrinter` for formatting

#### Benefits:
- Single Responsibility for printing
- Easy to change output format
- Testable formatting logic

## Design Patterns Applied

### 1. Strategy Pattern
```java
public interface DiscountStrategy {
    double calculateDiscount(double amount, DiscountCode discountCode);
}
```

### 2. Factory Pattern
```java
public class DiscountStrategyFactory {
    public static DiscountStrategy createStrategy(CustomerType customerType) {
        return switch (customerType) {
            case REGULAR -> new RegularDiscountStrategy();
            case VIP -> new VipDiscountStrategy();
        };
    }
}
```

### 3. Builder Pattern
```java
PricingRequest request = PricingRequest.builder()
    .prices(Arrays.asList(100.0, 50.0))
    .quantities(Arrays.asList(2, 1))
    .customerType(CustomerType.VIP)
    .discountCode("SAVE20")
    .build();
```

### 4. Parameter Object Pattern
`PricingRequest` encapsulates all pricing parameters into a single object.

## SOLID Principles Applied

### Single Responsibility Principle (SRP)
- Each class has one reason to change
- `SubtotalCalculator` only calculates subtotals
- `InvoicePrinter` only handles printing

### Open/Closed Principle (OCP)
- Open for extension, closed for modification
- New discount types can be added without changing existing code
- New customer types can be added via new strategies

### Liskov Substitution Principle (LSP)
- `DiscountStrategy` implementations are interchangeable
- All strategies can be used polymorphically

### Interface Segregation Principle (ISP)
- Small, focused interfaces
- `DiscountStrategy` has only discount-related methods

### Dependency Inversion Principle (DIP)
- High-level modules don't depend on low-level modules
- Both depend on abstractions (interfaces)

## Testing Improvements

### Before Refactoring:
- Hard to test individual calculations
- Tests require complex setup
- Can't mock dependencies

### After Refactoring:
- Each component can be tested independently
- Easy to mock dependencies
- Clear test structure
- Better test coverage

## Performance Considerations

### Memory Usage:
- Slight increase due to more objects
- Negligible impact for typical usage
- Benefits outweigh costs

### Execution Speed:
- Minimal overhead from additional method calls
- Cleaner code allows JVM optimizations
- Better cache locality with focused methods

## Maintenance Benefits

### Code Readability:
- Self-documenting code
- Clear separation of concerns
- Meaningful class and method names

### Extensibility:
- Easy to add new discount types
- Simple to modify calculation rules
- Straightforward to add new output formats

### Debugging:
- Isolated components make debugging easier
- Clear stack traces
- Focused error messages

## Before vs After Comparison

### Before (Monolithic):
```java
public PricingResult calculatePrice(Order order) {
    // 50+ lines of mixed logic
    // Subtotal calculation
    // Discount calculation with magic numbers
    // Tax calculation
    // Final price calculation
    return new PricingResult(subtotal, discountAmount, tax, finalPrice);
}
```

### After (Clean Architecture):
```java
public PricingResult calculatePrice(PricingRequest request) {
    double subtotal = subtotalCalculator.calculateSubtotal(request);
    double discountAmount = discountStrategy.calculateDiscount(subtotal, request.getDiscountCode());
    double tax = taxCalculator.calculateTax(subtotal - discountAmount);
    return new PricingResult(subtotal, discountAmount, tax, subtotal - discountAmount + tax);
}
```

## Key Takeaways

1. **Small Steps**: Refactor incrementally with tests at each step
2. **Pattern Recognition**: Identify when patterns can improve code
3. **SOLID Principles**: Use principles as guides, not rigid rules
4. **Test Coverage**: Maintain tests throughout refactoring
5. **Code Smells**: Learn to recognize and eliminate them

## Further Improvements Possible

1. **Dependency Injection**: Use Spring/Guice for DI
2. **Configuration**: Externalize rates to properties files
3. **Validation**: Add comprehensive input validation
4. **Logging**: Add proper logging for debugging
5. **Caching**: Cache discount calculations for performance
6. **Async Processing**: For large order processing
