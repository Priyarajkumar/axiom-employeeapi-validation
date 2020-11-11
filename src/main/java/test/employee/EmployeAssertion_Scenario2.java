package test.employee;

import commonmethods.Emp_CommonMethods;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utilities.ConfigUtils;
import utilities.ExcelUtils;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class EmployeAssertion_Scenario2 extends ExcelUtils {
    Emp_CommonMethods cmnMethod = new Emp_CommonMethods();
    ConfigUtils configUtils = new ConfigUtils();
    Response response = null;

    @Test(dataProvider = "EmployeeId", priority = 3,groups = {"getSingleEmpResponse"})
    public void getEmpApiResponse(HashMap<String, String> testData) throws IOException {
        String apiURI = configUtils.getPropertyValue("apiURI");
        String empId = testData.get("Data1");
        String configEndpoint = configUtils.getPropertyValue("getSingleEmployeeEndpoint");
        configEndpoint = configEndpoint.replace("{id}", empId);
        String url = apiURI + configEndpoint;
        response = cmnMethod.getApiResponse(url);
        System.out.println(response);
    }

    @Test(priority = 4,dependsOnGroups = {"getSingleEmpResponse"})
    public void verifyStatusCode() {
        System.out.println(response.getStatusCode());
        cmnMethod.assertStatusCode(response);
    }

    @Test(priority = 5,dependsOnGroups = {"getSingleEmpResponse"})
    public void verifyMessage() {
        String apiMessage = cmnMethod.getStringfromJson(response,"message");
        Assert.assertEquals("Successfully! Record has been fetched.", apiMessage);
    }

    @Test(priority = 6,dependsOnGroups = {"getSingleEmpResponse"})
    public void verifyResponsePattern() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("response.json"));
    }
}
