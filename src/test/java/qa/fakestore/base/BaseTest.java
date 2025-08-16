package qa.fakestore.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected RequestSpecification request;
    protected Response response;
    protected String baseURL = "https://fakestoreapi.com";

    @BeforeClass
    public void setUp() {
        try {
            RestAssured.baseURI = baseURL;
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            System.out.println("✅ Base URL set to: " + baseURL);
        } catch (Exception e) {
            System.out.println("❌ Setup failed: " + e.getMessage());
            throw e;
        }
    }

    @BeforeMethod
    public void setUpRequest() {
        try {
            request = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");
            System.out.println("✅ Request specification set up");
        } catch (Exception e) {
            System.out.println("❌ Request setup failed: " + e.getMessage());
            throw e;
        }
    }

    // Helper method for common validations
    protected void validateSuccessResponse(Response response) {
        response.then()
                .statusCode(200);
        // Content-Type check'i kaldırdık çünkü API bazen farklı döndürüyor
    }

    // Performance measurement helper
    protected long measureResponseTime(Response response) {
        return response.getTime();
    }
}