package test.employee;

import commonmethods.Emp_CommonMethods;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utilities.ConfigUtils;
import org.testng.Reporter;

import java.io.IOException;
import java.util.List;

public class EmployeeAssertion_Scenario1 {

    Emp_CommonMethods cmnMethod = new Emp_CommonMethods();
    ConfigUtils configUtils = new ConfigUtils();
    Response response = null;

    @Test
    public void getallEmpApiResponse() throws IOException {
        Reporter.log("Retrive data from config file");
        System.out.println("Retrive data from config file");
        String apiURI = configUtils.getPropertyValue("apiURI");
        String configEndpoint = configUtils.getPropertyValue("getAllEmployeesEndpoint");
        String url = apiURI + configEndpoint;
        response = cmnMethod.getApiResponse(url);

        Reporter.log("Get all Employee Data");
        System.out.println("Get all Employee Data");

        Reporter.log(response.getBody().asString());
        System.out.println(response.getBody().asString());
    }

    @Test(dependsOnMethods = {"getallEmpApiResponse"})
    public void assertEmpStatusCode() {
        cmnMethod.assertStatusCode(response);
        Reporter.log("The Status code is: "+response.getStatusCode()+" verified");
        System.out.println("The Status code is: "+response.getStatusCode()+" verified");
    }

    @Test(dependsOnMethods = {"getallEmpApiResponse"})
    public void verifyProfileName() {
        List<String> values = cmnMethod.getValuesfromArray(response, "data", "profile_image");
        int iterater = 1;
        for (String value : values) {
            iterater=iterater+1;
            if (value != "") {
                Assert.fail("This Employee has value for Profile Image , EmpId: " + cmnMethod.getValuesfromArray(response, "data", "id").get(0).toString());
            } else {
                if (iterater == values.size()) {
                    System.out.println("Profile Image is Empty for all Employees");
                    Reporter.log("Profile Image is Empty for all Employees");
                }
            }
        }
    }


}
