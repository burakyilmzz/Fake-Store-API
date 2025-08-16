package qa.fakestore.tests;

import qa.fakestore.base.BaseTest;
import qa.fakestore.models.Product;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

@Epic("Fake Store API Testing")
@Feature("Product Management")
public class ProductAPITests extends BaseTest {

    @Test(priority = 1)
    @Story("Get All Products")
    @Description("Verify that all products can be retrieved successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllProducts() {
        response = request.when().get("/products");

        validateSuccessResponse(response);

        // Validate response structure
        Product[] products = response.as(Product[].class);
        Assert.assertTrue(products.length > 0, "Products list should not be empty");

        // Validate first product structure
        Product firstProduct = products[0];
        Assert.assertNotNull(firstProduct.getTitle(), "Product title should not be null");
        Assert.assertTrue(firstProduct.getPrice() > 0, "Product price should be positive");
        Assert.assertNotNull(firstProduct.getCategory(), "Product category should not be null");

        // Performance validation
        long responseTime = measureResponseTime(response);
        Assert.assertTrue(responseTime < 3000,
                "Response time should be less than 3 seconds. Actual: " + responseTime + "ms");

        System.out.println("✅ Retrieved " + products.length + " products successfully");
        System.out.println("🕐 Response time: " + responseTime + "ms");
    }

    @Test(priority = 2)
    @Story("Get Single Product")
    @Description("Verify that a single product can be retrieved by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSingleProduct() {
        int productId = 1;
        response = request.when().get("/products/" + productId);

        validateSuccessResponse(response);

        Product product = response.as(Product.class);
        Assert.assertEquals(product.getId(), productId, "Product ID should match requested ID");
        Assert.assertNotNull(product.getTitle(), "Product title should not be null");
        Assert.assertTrue(product.getPrice() > 0, "Product price should be positive");
        Assert.assertNotNull(product.getRating(), "Product rating should not be null");
        Assert.assertTrue(product.getRating().getRate() >= 0 && product.getRating().getRate() <= 5,
                "Rating should be between 0 and 5");

        System.out.println("✅ Product retrieved: " + product.getTitle());
    }

    @Test(priority = 3)
    @Story("Get Product Categories")
    @Description("Verify that all product categories can be retrieved")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCategories() {
        response = request.when().get("/products/categories");

        validateSuccessResponse(response);

        String[] categories = response.as(String[].class);
        Assert.assertTrue(categories.length > 0, "Categories list should not be empty");

        List<String> expectedCategories = Arrays.asList("electronics", "jewelery", "men's clothing", "women's clothing");
        for (String category : categories) {
            Assert.assertTrue(expectedCategories.contains(category),
                    "Category '" + category + "' should be in expected categories");
        }

        System.out.println("✅ Retrieved categories: " + Arrays.toString(categories));
    }

    @Test(priority = 4, dataProvider = "categoryData")
    @Story("Get Products by Category")
    @Description("Verify that products can be filtered by category")
    @Severity(SeverityLevel.NORMAL)
    public void testGetProductsByCategory(String category) {
        response = request.when().get("/products/category/" + category);

        validateSuccessResponse(response);

        Product[] products = response.as(Product[].class);
        Assert.assertTrue(products.length > 0, "Products list should not be empty for category: " + category);

        // Verify all products belong to the requested category
        for (Product product : products) {
            Assert.assertEquals(product.getCategory(), category,
                    "All products should belong to category: " + category);
        }

        System.out.println("✅ Found " + products.length + " products in category: " + category);
    }

    @Test(priority = 5)
    @Story("Add New Product")
    @Description("Verify that a new product can be added successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddNewProduct() {
        Product newProduct = new Product(
                "QA Test Product",
                29.99,
                "This is a test product created by QA automation",
                "electronics",
                "https://i.pravatar.cc/300"
        );

        response = request
                .body(newProduct)
                .when()
                .post("/products");

        validateSuccessResponse(response);

        Product createdProduct = response.as(Product.class);
        Assert.assertNotNull(createdProduct.getId(), "Created product should have an ID");
        Assert.assertEquals(createdProduct.getTitle(), newProduct.getTitle(), "Title should match");
        Assert.assertEquals(createdProduct.getPrice(), newProduct.getPrice(), 0.01, "Price should match");

        System.out.println("✅ Product created with ID: " + createdProduct.getId());
    }

    @Test(priority = 6)
    @Story("Update Product")
    @Description("Verify that an existing product can be updated")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateProduct() {
        int productId = 1;
        Product updatedProduct = new Product(
                "Updated Test Product",
                39.99,
                "This product has been updated by QA automation",
                "electronics",
                "https://i.pravatar.cc/400"
        );

        response = request
                .body(updatedProduct)
                .when()
                .put("/products/" + productId);

        validateSuccessResponse(response);

        Product resultProduct = response.as(Product.class);
        Assert.assertEquals(resultProduct.getId(), productId, "Product ID should remain the same");
        Assert.assertEquals(resultProduct.getTitle(), updatedProduct.getTitle(), "Title should be updated");

        System.out.println("✅ Product updated: " + resultProduct.getTitle());
    }

    @Test(priority = 7)
    @Story("Delete Product")
    @Description("Verify that a product can be deleted")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteProduct() {
        int productId = 1;

        response = request.when().delete("/products/" + productId);

        validateSuccessResponse(response);

        Product deletedProduct = response.as(Product.class);
        Assert.assertNotNull(deletedProduct, "Response should contain deleted product info");

        System.out.println("✅ Product deleted: " + deletedProduct.getTitle());
    }

    @Test(priority = 8)
    @Story("Get Non-Existent Product")
    @Description("Verify API behavior when requesting non-existent product")
    @Severity(SeverityLevel.MINOR)
    public void testGetNonExistentProduct() {
        int nonExistentId = 999;

        response = request.when().get("/products/" + nonExistentId);

        // Note: This API returns 200 with null, which is not ideal behavior
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        String responseBody = response.getBody().asString();
        Assert.assertEquals(responseBody, "null", "Response should be null for non-existent product");

        System.out.println("⚠️ Non-existent product test completed - API returns null instead of 404");
    }

    @Test(priority = 9)
    @Story("Data Validation")
    @Description("Verify data integrity of all products")
    @Severity(SeverityLevel.NORMAL)
    public void testProductDataIntegrity() {
        response = request.when().get("/products");

        Product[] products = response.as(Product[].class);

        for (Product product : products) {
            // Validate required fields
            Assert.assertNotNull(product.getTitle(), "Product title should not be null for ID: " + product.getId());
            Assert.assertTrue(product.getPrice() > 0, "Product price should be positive for ID: " + product.getId());
            Assert.assertNotNull(product.getCategory(), "Product category should not be null for ID: " + product.getId());

            // Validate rating
            if (product.getRating() != null) {
                Assert.assertTrue(product.getRating().getRate() >= 0 && product.getRating().getRate() <= 5,
                        "Rating should be between 0-5 for product ID: " + product.getId());
                Assert.assertTrue(product.getRating().getCount() >= 0,
                        "Rating count should be non-negative for product ID: " + product.getId());
            }
        }

        System.out.println("✅ Data integrity validated for " + products.length + " products");
    }

    @Test(priority = 10)
    @Story("Performance Testing")
    @Description("Verify API performance under normal load")
    @Severity(SeverityLevel.NORMAL)
    public void testAPIPerformance() {
        long totalTime = 0;
        int iterations = 5;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            response = request.when().get("/products");
            long endTime = System.currentTimeMillis();

            Assert.assertEquals(response.getStatusCode(), 200, "Request " + (i + 1) + " should be successful");

            long requestTime = endTime - startTime;
            totalTime += requestTime;

            System.out.println("Request " + (i + 1) + " completed in: " + requestTime + "ms");
        }

        long averageTime = totalTime / iterations;
        Assert.assertTrue(averageTime < 3000,
                "Average response time should be less than 3 seconds. Actual: " + averageTime + "ms");

        System.out.println("✅ Performance test completed. Average response time: " + averageTime + "ms");
    }

    @DataProvider(name = "categoryData")
    public Object[][] getCategoryData() {
        return new Object[][]{
                {"electronics"},
                {"jewelery"},
                {"men's clothing"},
                {"women's clothing"}
        };
    }
}