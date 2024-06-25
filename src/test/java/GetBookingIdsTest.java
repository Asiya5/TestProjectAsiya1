import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBookingIdsTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void testGetBookingIdsByName() {
        Response response = RestAssured
                .given()
                .queryParam("firstname", "Jim")
                .queryParam("lastname", "Brown")
                .get("/booking");

        response.then().statusCode(200);
        Assert.assertTrue(response.jsonPath().getList("bookingid").size() > 0);
    }

    @Test
    public void testGetBookingIdsByDate() {
        Response response = RestAssured
                .given()
                .queryParam("checkin", "2023-06-01")
                .queryParam("checkout", "2023-06-10")
                .get("/booking");

        response.then().statusCode(200);
        Assert.assertTrue(response.jsonPath().getList("bookingid").size() > 0);
    }

}

