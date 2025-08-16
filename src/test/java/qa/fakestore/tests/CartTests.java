package qa.fakestore.tests;

import qa.fakestore.base.BaseTest;
import io.qameta.allure.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Fake Store API Testing")
@Feature("Cart Management")
public class CartTests extends BaseTest {

    @Test(priority = 1)
    @Story("Get All Carts")
    @Description("Verify that all carts can be retrieved")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAllCarts() {
        response = request.when().get("/carts");

        validateSuccessResponse(response);

        JSONArray carts = new JSONArray(response.getBody().asString());
        Assert.assertTrue(carts.length() > 0, "Carts list should not be empty");

        // Validate cart structure
        JSONObject firstCart = carts.getJSONObject(0);
        Assert.assertTrue(firstCart.has("id"), "Cart should have id");
        Assert.assertTrue(firstCart.has("userId"), "Cart should have userId");
        Assert.assertTrue(firstCart.has("date"), "Cart should have date");
        Assert.assertTrue(firstCart.has("products"), "Cart should have products");

        System.out.println("✅ Retrieved " + carts.length() + " carts successfully");
    }

    @Test(priority = 2)
    @Story("Add New Cart")
    @Description("Verify that a new cart can be created")
    @Severity(SeverityLevel.NORMAL)
    public void testAddNewCart() {
        JSONObject cartData = new JSONObject();
        cartData.put("userId", 5);
        cartData.put("date", "2024-01-01");

        JSONArray products = new JSONArray();
        JSONObject product1 = new JSONObject();
        product1.put("productId", 5);
        product1.put("quantity", 1);
        products.put(product1);

        JSONObject product2 = new JSONObject();
        product2.put("productId", 1);
        product2.put("quantity", 5);
        products.put(product2);

        cartData.put("products", products);

        response = request
                .body(cartData.toString())
                .when()
                .post("/carts");

        validateSuccessResponse(response);

        JSONObject createdCart = new JSONObject(response.getBody().asString());
        Assert.assertTrue(createdCart.has("id"), "Created cart should have an ID");

        System.out.println("✅ Cart created with ID: " + createdCart.getInt("id"));
    }
}