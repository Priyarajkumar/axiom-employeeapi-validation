package commonmethods;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.Reporter;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Emp_CommonMethods {

    public List getValuesfromArray(Response response, String arrayName, String Key) {
        return response.jsonPath().getList(arrayName + "." + Key);
    }

    public String getStringfromJson(Response response, String Key) {
        return response.jsonPath().getString(Key);
    }

    public void assertStatusCode(Response response) {
        Assert.assertEquals(200, response.statusCode());
    }

    public Response getApiResponse(String url) {
        Response response = null;
        try {
            response = given().header("content-Type", ContentType.JSON, "Accept", ContentType.JSON).when()
                    .get(url).then().extract().response();
            int responseStatuscode = response.statusCode();
            if (responseStatuscode != 200) {
                Assert.fail("API Failed with the Statuscode: " + responseStatuscode);
            }else{
                System.out.println("The Status code is "+ responseStatuscode);
                Reporter.log("The Status code is "+ responseStatuscode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e.printStackTrace()");
        }
        return response;
    }
}
