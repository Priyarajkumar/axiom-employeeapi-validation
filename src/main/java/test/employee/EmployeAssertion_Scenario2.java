package test.employee;

import commonmethods.Emp_CommonMethods;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.Reporter;
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

    @Test(dataProvider = "EmployeeId")
    public void getSingleEmpApiResponse(HashMap<String, String> testData) throws IOException {
        Reporter.log("Retrive data from config file");
        System.out.println("Retrive data from config file");
        String apiURI = configUtils.getPropertyValue("apiURI");
        String configEndpoint = configUtils.getPropertyValue("getSingleEmployeeEndpoint");

        Reporter.log("Retrive data from Excel file: " + testData.get("EmployeeId"));
        System.out.println("Retrive data from Excel file: " + testData.get("EmployeeId"));
        String empId = testData.get("EmployeeId");
        configEndpoint = configEndpoint.replace("{id}", empId);
        String url = apiURI + configEndpoint;
        response = cmnMethod.getApiResponse(url);

        Reporter.log("Get single Employee Data");
        System.out.println("Get single Employee Data");
        Reporter.log(response.getBody().asString());
        System.out.println(response.getBody().asString());
    }

    @Test(dependsOnMethods = {"getSingleEmpApiResponse"})
    public void verifyStatusCode() {
        cmnMethod.assertStatusCode(response);
        Reporter.log("The Status code is: " + response.getStatusCode() + " verified");
        System.out.println("The Status code is: " + response.getStatusCode() + " verified");
    }

    @Test(dependsOnMethods = {"getSingleEmpApiResponse"})
    public void verifyMessage() {
        String apiMessage = cmnMethod.getStringfromJson(response, "message");
        System.out.println("Validate Success Message, Message is: " + apiMessage);
        Reporter.log("Validate Success Message, Message is: " + apiMessage);
        Assert.assertEquals("Successfully! Record has been fetched.", apiMessage);
    }

    @Test(dependsOnMethods = {"getSingleEmpApiResponse"})
    public void verifyResponsePattern() {
        System.out.println("Validate Response pattern:      " + response.getBody().asString());
        Reporter.log("Validate Response pattern:      " + response.getBody().asString());
        response.then().assertThat().body(matchesJsonSchemaInClasspath("response.json"));
    }
}
