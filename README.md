# 🛍️ Fake Store API Test Automation Framework

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-red.svg)](https://testng.org/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.3.2-green.svg)](https://rest-assured.io/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)

## 📋 Project Overview

Comprehensive API test automation framework for **Fake Store API** demonstrating real-world e-commerce testing scenarios. This project showcases end-to-end API testing capabilities including CRUD operations, authentication, data validation, and performance testing.

## 🎯 Key Features

- **Complete CRUD Testing**: Create, Read, Update, Delete operations
- **Authentication Testing**: Login validation and security scenarios
- **Data Integrity Validation**: JSON response validation and data consistency
- **Performance Testing**: Response time measurement and analysis
- **Negative Testing**: Error handling and edge case validation
- **Parallel Execution**: Multi-threaded test execution support
- **Comprehensive Reporting**: Allure integration for detailed test reports

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Programming Language |
| **TestNG** | 7.8.0 | Testing Framework |
| **RestAssured** | 5.3.2 | API Testing Library |
| **Jackson** | 2.15.2 | JSON Processing |
| **Allure** | 2.24.0 | Test Reporting |
| **Maven** | 3.6+ | Build Management |

## 📊 Test Results Summary

```
✅ Total Tests: 18
✅ Passed: 12 (67%)
❌ Failed: 6 (33%)
⚡ Average Response Time: 210ms
🎯 API Coverage: 100% CRUD Operations
```

### Test Categories Covered:

- **Product Management** (8 tests)
    - Get all products
    - Get product by ID
    - Create new product
    - Update existing product
    - Delete product
    - Category-based filtering

- **Authentication** (4 tests)
    - Valid login scenarios
    - Invalid credentials handling
    - Empty credential validation
    - Token validation

- **Shopping Cart** (3 tests)
    - Add items to cart
    - Retrieve cart contents
    - Cart data validation

- **Performance & Data Integrity** (3 tests)
    - Response time benchmarking
    - Concurrent request handling
    - Data consistency validation

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

### Installation & Setup

```bash
# Clone the repository
git clone https://github.com/yourusername/fake-store-api-testing.git
cd fake-store-api-testing

# Install dependencies
mvn clean install

# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=ProductAPITests

# Run with custom TestNG suite
mvn test -DsuiteXmlFile=testng.xml
```

## 📁 Project Structure

```
src/
├── main/java/
│   └── qa/fakestore/
│       ├── base/
│       │   └── BaseTest.java          # Base test configuration
│       └── models/
│           ├── Product.java           # Product data model
│           └── User.java              # User data model
└── test/java/
    └── qa/fakestore/
        ├── tests/
        │   ├── ProductAPITests.java   # Product CRUD tests
        │   ├── AuthenticationTests.java # Auth validation tests
        │   └── CartTests.java         # Shopping cart tests
        └── runner/
            └── TestRunner.java        # Test execution runner
```

## 🔧 Configuration

### TestNG Configuration (testng.xml)
```xml
<suite name="FakeStoreAPITestSuite" parallel="classes" thread-count="3">
    <test name="API Tests">
        <classes>
            <class name="qa.fakestore.tests.ProductAPITests"/>
            <class name="qa.fakestore.tests.AuthenticationTests"/>
            <class name="qa.fakestore.tests.CartTests"/>
        </classes>
    </test>
</suite>
```





## 📈 Test Execution Examples

### Successful API Calls
```
✅ Retrieved 20 products successfully
✅ Product created with ID: 21
✅ Product updated: "Updated Test Product"
✅ Product deleted successfully
✅ Performance test completed. Average response time: 210ms
```

### API Endpoints Tested
- `GET /products` - Retrieve all products
- `GET /products/{id}` - Get specific product
- `GET /products/categories` - Get product categories
- `POST /products` - Create new product
- `PUT /products/{id}` - Update product
- `DELETE /products/{id}` - Delete product
- `POST /auth/login` - User authentication
- `GET /carts` - Shopping cart operations

## 📋 Sample Test Cases


## 🎯 Key QA Skills Demonstrated

- **API Testing Expertise**: Comprehensive REST API validation
- **Test Automation**: Framework design and implementation
- **Data-Driven Testing**: Dynamic test data generation
- **Performance Testing**: Response time analysis and benchmarking
- **Negative Testing**: Error scenario validation
- **CI/CD Ready**: Maven integration for continuous testing
- **Reporting**: Professional test result documentation

## 🐛 Known Issues & Limitations

Since Fake Store API is a mock service:
- Authentication endpoints return simulated responses
- Some PUT/DELETE operations don't persist data
- Limited rate limiting testing capabilities
- Mock data constraints for edge case testing

## 📊 Reporting

### Generate Allure Reports
```bash
# Generate Allure report
mvn allure:report

# Open report in browser
mvn allure:serve
```

### TestNG HTML Reports
Reports are automatically generated in `test-output/index.html`

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📞 Contact

**QA Engineer**: [BURAK YILMAZ]  
**Email**: [burak.yilmzz11@gmail.com]  
**LinkedIn**: [www.linkedin.com/in/burakyilmazqa/]  


---

⭐ **Star this repository if you found it helpful!**

## 🏆 Why This Project Stands Out

- **Production-Ready Framework**: Enterprise-level automation structure
- **Comprehensive Coverage**: Multiple testing types in one framework
- **Real-World Scenarios**: E-commerce domain testing experience
- **Professional Documentation**: Clear, detailed project presentation
- **Performance Metrics**: Quantifiable test results and benchmarks
- **Scalable Architecture**: Easy to extend and maintain

*This project demonstrates advanced QA automation skills suitable for senior-level positions in software testing and quality assurance.*