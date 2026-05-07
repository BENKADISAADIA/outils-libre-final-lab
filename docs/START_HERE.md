# START HERE - Quick Reference Guide

## 🚀 Quick Start

### Prerequisites
- Java 11+ installed
- Gradle 7.0+ installed
- Git installed
- Python 3.8+ (for integration tests)

### Setup Commands
```bash
# Clone and setup
git clone <repository-url>
cd windsurf-project

# Build and test
./gradlew build
./gradlew test

# Run the application
./gradlew run

# Setup integration tests
cd integration_tests
pip install -r requirements.txt
```

## 📁 Project Overview

This is a **Pricing & Discount Engine** lab that demonstrates:
- Complete refactoring from bad design to clean architecture
- Design patterns: Strategy, Factory, Builder, Parameter Object
- SOLID principles in practice
- Git workflow with meaningful commits
- Testing with JUnit 5 and Python integration tests

## 🎯 Learning Objectives

1. **Identify Code Smells** → Learn to recognize problematic code
2. **Apply Design Patterns** → Strategy, Factory, Builder patterns
3. **Follow SOLID Principles** → Real-world application
4. **Refactor Safely** → With comprehensive test coverage
5. **Git Best Practices** → Meaningful commit messages

## 🏗️ Architecture Evolution

### Before (Monolithic)
```
PricingEngine.java (115 lines)
├── calculatePrice() - 50+ lines
├── processAndPrint() - Mixed concerns
└── doStuff() - Poor naming
```

### After (Clean Architecture)
```
PricingEngineFinal.java (64 lines)
├── Strategy Pattern → Discount calculations
├── Factory Pattern → Object creation
├── Builder Pattern → Request construction
├── Service Layer → Business logic
└── Domain Models → Type safety
```

## 📋 Lab Workflow

### Phase 1: Setup (Commits 1-3)
```bash
git log --oneline -3
# feat: initialize Gradle app
# add initial poorly-designed PricingEngine  
# add unit tests for PricingEngine
```

**What you'll learn:**
- Gradle project setup
- Identifying code smells
- Writing comprehensive tests

### Phase 2: Refactoring (Commits 4-15)
```bash
git log --oneline -12 --skip=3
# refactor: extract magic numbers/strings into constants
# add DiscountStrategy interface
# add RegularDiscountStrategy
# add VipDiscountStrategy
# add DiscountStrategyFactory
# refactor: delegate discount logic to DiscountStrategyFactory using Strategy pattern
# add Invoice data class
# add InvoicePrinter
# refactor: extract computeTotal() method & delegate to Invoice/InvoicePrinter
# add PricingRequest parameter object with Builder
# refactor: introduce PricingRequest parameter object
```

**What you'll learn:**
- Step-by-step refactoring
- Design pattern application
- SOLID principles in action

### Phase 3: Documentation (Commits 16-17)
```bash
git log --oneline -2 --skip=15
# docs: add comprehensive refactoring guide with examples
# docs: rewrite README with complete lab guide
```

**What you'll learn:**
- Technical documentation
- Knowledge transfer

## 🔧 Common Tasks

### Run Tests
```bash
# Unit tests (Java)
./gradlew test

# Integration tests (Python)
cd integration_tests
python -m pytest test_pricing_integration.py
```

### Build Application
```bash
# Create executable JAR
./gradlew jar

# Run with Java
java -jar build/libs/pricing-discount-engine-1.0-SNAPSHOT.jar
```

### Code Analysis
```bash
# Check code quality
./gradlew checkstyleMain

# Generate test report
./gradlew test --info
```

## 📚 Key Files to Study

### For Learning Refactoring
1. **`PricingEngine.java`** → See the problems
2. **`PricingEngineFinal.java`** → See the solution
3. **`docs/REFACTORING_GUIDE.md`** → Step-by-step guide

### For Design Patterns
1. **`strategy/`** → Strategy pattern implementation
2. **`factory/DiscountStrategyFactory.java`** → Factory pattern
3. **`model/PricingRequest.java`** → Builder pattern

### For Testing
1. **`PricingEngineTest.java`** → Unit testing examples
2. **`integration_tests/`** → Cross-language testing

## 🎯 Code Examples

### Using Original (Bad) Design
```java
PricingEngine engine = new PricingEngine();
List<Double> prices = Arrays.asList(100.0, 50.0);
List<Integer> quantities = Arrays.asList(2, 1);
PricingEngine.Order order = new PricingEngine.Order(
    prices, quantities, "VIP", "SAVE20");
PricingEngine.PricingResult result = engine.calculatePrice(order);
```

### Using Refactored (Good) Design
```java
PricingEngineFinal engine = new PricingEngineFinal();
PricingRequest request = PricingRequest.builder()
    .prices(Arrays.asList(100.0, 50.0))
    .quantities(Arrays.asList(2, 1))
    .customerType(CustomerType.VIP)
    .discountCode("SAVE20")
    .build();

PricingResult result = engine.calculatePrice(request);
Invoice invoice = engine.createInvoice(request);
engine.processAndPrintInvoice(request);
```

## 🔍 Code Smells to Identify

### In Original Code
- ❌ **Long Method**: `calculatePrice()` does everything
- ❌ **Magic Numbers**: 0.1, 0.2, 0.08 scattered
- ❌ **Poor Naming**: `doStuff()` method
- ❌ **Mixed Concerns**: Calculation + printing
- ❌ **No Type Safety**: String-based customer types

### In Refactored Code
- ✅ **Single Responsibility**: Each class has one purpose
- ✅ **Constants**: All magic numbers extracted
- ✅ **Clear Naming**: Self-documenting code
- ✅ **Separated Concerns**: Distinct layers
- ✅ **Type Safety**: Enums for all types

## 🎓 Design Patterns Applied

### 1. Strategy Pattern
```java
public interface DiscountStrategy {
    double calculateDiscount(double amount, DiscountCode discountCode);
}
```
**Benefits**: Easy to add new discount types, follows OCP

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
**Benefits**: Centralized creation, hides implementation

### 3. Builder Pattern
```java
PricingRequest request = PricingRequest.builder()
    .prices(Arrays.asList(100.0, 50.0))
    .customerType(CustomerType.VIP)
    .discountCode("SAVE20")
    .build();
```
**Benefits**: Clean construction, immutable objects

## 📊 Before vs After Metrics

| Metric | Before | After |
|--------|--------|-------|
| Cyclomatic Complexity | 15+ | 3-5 |
| Lines of Code | 115 (1 class) | 600+ (15+ classes) |
| Test Coverage | Basic | Comprehensive |
| Extensibility | Limited | High |
| Maintainability | Poor | Excellent |

## 🛠️ Troubleshooting

### Build Issues
```bash
# Clean and rebuild
./gradlew clean build

# Check Java version
java -version  # Should be 11+

# Check Gradle version
./gradlew --version  # Should be 7.0+
```

### Test Failures
```bash
# Run specific test
./gradlew test --tests PricingEngineTest

# Debug test output
./gradlew test --debug
```

### Integration Test Issues
```bash
# Install Python dependencies
pip install -r integration_tests/requirements.txt

# Run with verbose output
python -m pytest -v integration_tests/test_pricing_integration.py
```

## 📖 Further Reading

### Documentation Files
1. **`README.md`** → Complete project overview
2. **`docs/REFACTORING_GUIDE.md`** → Detailed refactoring process
3. **`docs/PROJECT_STRUCTURE.md`** → Architecture explanation
4. **`docs/COMMIT_SUMMARY.md`** → Learning outcomes

### External Resources
- [Clean Code by Robert C. Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350884)
- [Refactoring by Martin Fowler](https://refactoring.com/)
- [Design Patterns by Gang of Four](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)

## 🎯 Next Steps

### After Completing This Lab
1. **Add New Features**: Try adding a new customer type
2. **Performance Testing**: Measure before/after performance
3. **Dependency Injection**: Add Spring Framework
4. **API Development**: Create REST endpoints
5. **Database Integration**: Add persistence layer

### Advanced Topics
- Microservices architecture
- Event-driven design
- CQRS pattern
- Domain-driven design

## 📞 Getting Help

### Check These First
1. **Git History**: `git log --oneline` to see evolution
2. **Test Files**: See usage examples
3. **Documentation**: Comprehensive guides in `docs/`
4. **Code Comments**: Inline explanations

### Common Questions
- **Q: Why so many classes?** → Single Responsibility Principle
- **Q: Is this over-engineered?** → It's a learning example
- **Q: Can I simplify this?** → Yes, for production use

## ✅ Success Criteria

You've successfully completed this lab when you can:

- [ ] Identify all code smells in the original code
- [ ] Explain each refactoring step and its benefits
- [ ] Apply the same patterns to new problems
- [ ] Write clean, testable code following SOLID principles
- [ ] Use Git effectively with meaningful commits
- [ ] Document your refactoring process

---

**Happy Learning! 🎓**

Remember: Refactoring is a skill that improves with practice. Start small, test often, and focus on making the code a little better each time.
