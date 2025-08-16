package qa.fakestore.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleDebugTest {

    @Test
    public void simpleGetTest() {
        try {
            // Basit bir GET request
            RestAssured.baseURI = "https://fakestoreapi.com";

            Response response = RestAssured.given()
                    .when()
                    .get("/products");

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Time: " + response.getTime() + "ms");
            System.out.println("Response Body: " + response.getBody().asString().substring(0, 100) + "...");

            Assert.assertEquals(response.getStatusCode(), 200);

            System.out.println("✅ Simple test PASSED!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}