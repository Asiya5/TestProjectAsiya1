import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateBookingTest {

    private static int bookingId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void testCreateBooking() {
        String requestBody = """
                {
                    "firstname" : "Jim",
                    "lastname" : "Brown",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2023-06-01",
                        "checkout" : "2023-06-10"
                    },
                    "additionalneeds" : "Breakfast"
                }
                """;

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/booking");

        response.then().statusCode(200);
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertNotEquals(bookingId, 0);
    }

    @Test(dependsOnMethods = "testCreateBooking")
    public void testGetBooking() {
        Response response = RestAssured
                .given()
                .get("/booking/" + bookingId);

        response.then().statusCode(200);
        String firstname = response.jsonPath().getString("firstname");
        String lastname = response.jsonPath().getString("lastname");
        Assert.assertEquals(firstname, "Jim");
        Assert.assertEquals(lastname, "Brown");

    }
}
