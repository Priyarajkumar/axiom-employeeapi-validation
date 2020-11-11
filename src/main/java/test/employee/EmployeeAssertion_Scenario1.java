package test.employee;

import commonmethods.Emp_CommonMethods;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static io.restassured.RestAssured.given;

import utilities.ConfigUtils;

public class EmployeeAssertion_Scenario1 {
    Emp_CommonMethods cmnMethod = new Emp_CommonMethods();
    ConfigUtils configUtils = new ConfigUtils();
    Response response = null;


    @Test(priority = 0 , groups = {"getAllEmpResponse"})
    public void getEmpApiResponse() throws IOException {
        String apiURI = configUtils.getPropertyValue("apiURI");
        String configEndpoint = configUtils.getPropertyValue("getAllEmployeesEndpoint");
        String url = apiURI+configEndpoint;
        response = cmnMethod.getApiResponse(url);
        System.out.println(response);
    }

    @Test(priority = 1,dependsOnGroups = {"getAllEmpResponse"})
    public void assertEmpStatusCode() {
        cmnMethod.assertStatusCode(response);
    }

    @Test(priority = 2,dependsOnGroups = {"getAllEmpResponse"})
    public void verifyProfileName() {
        List<String> values = cmnMethod.getValuesfromArray(response, "data", "profile_image");
        for (String value : values) {
            if (value != "") {
                Assert.fail();
            }
        }
    }


}
