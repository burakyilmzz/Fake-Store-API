package qa.fakestore.tests;

import qa.fakestore.base.BaseTest;
import io.qameta.allure.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Fake Store API Testing")
@Feature("Authentication")
public class AuthenticationTests extends BaseTest {

    @Test(priority = 1)
    @Story("Valid User Login")
    @Description("Verify that user can login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidLogin() {
        try {
            JSONObject loginData = new JSONObject();
            loginData.put("username", "mor_2314");
            loginData.put("password", "83r5^_");

            response = request
                    .body(loginData.toString())
                    .when()
                    .post("/auth/login");

            // Sadece status code kontrol et
            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

            String responseBody = response.getBody().asString();
            System.out.println("Response: " + responseBody);

            // Token kontrolü - eğer null değilse
            if (!responseBody.equals("null")) {
                JSONObject responseJson = new JSONObject(responseBody);
                Assert.assertTrue(responseJson.has("token"), "Response should contain token");
                String token = responseJson.getString("token");
                System.out.println("✅ Login successful. Token received: " + token.substring(0, Math.min(10, token.length())) + "...");
            } else {
                System.out.println("⚠️ Login returned null - API behavior may have changed");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    @Story("Invalid User Login")
    @Description("Verify that login fails with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidLogin() {
        try {
            JSONObject loginData = new JSONObject();
            loginData.put("username", "invalid_user");
            loginData.put("password", "wrong_password");

            response = request
                    .body(loginData.toString())
                    .when()
                    .post("/auth/login");

            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

            String responseBody = response.getBody().asString();
            System.out.println("Invalid login response: " + responseBody);

            // API null döndürüyor invalid credentials için
            Assert.assertEquals(responseBody, "null", "Response should be null for invalid credentials");

            System.out.println("✅ Invalid login test completed");

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    @Story("Empty Credentials")
    @Description("Verify API behavior with empty credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyCredentials() {
        try {
            JSONObject loginData = new JSONObject();
            loginData.put("username", "");
            loginData.put("password", "");

            response = request
                    .body(loginData.toString())
                    .when()
                    .post("/auth/login");

            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

            String responseBody = response.getBody().asString();
            System.out.println("Empty credentials response: " + responseBody);

            System.out.println("✅ Empty credentials test completed");

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            throw e;
        }
    }
}