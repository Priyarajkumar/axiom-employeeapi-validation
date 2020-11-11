package commonmethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static io.restassured.RestAssured.given;

public class Emp_CommonMethods {

    public List getValuesfromArray(Response response, String arrayName, String Key) {
        List<String> values = response.jsonPath().getList(arrayName+"."+Key);
        return values;
    }

    public String getStringfromJson(Response response,String Key) {
        String value = response.jsonPath().getString(Key);
        return value;
    }

    public void assertStatusCode(Response response) {
        Assert.assertEquals(200, response.statusCode());
    }

    public Response getApiResponse(String url) {
        Response response = null;
        try {
             response = given().header("content-Type", ContentType.JSON, "Accept", ContentType.JSON).when()
                    .get(url).then().extract().response();
            if(response.statusCode() == 429){
                Assert.fail("Failed Because of too many request , Status Code: "+response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e.printStackTrace()");
        }
        return response;
    }
}
