package test.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import test.pojo.API;
import test.pojo.Courses;
import test.pojo.GetCourse;
import test.pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.groovy.json.internal.JsonParserCharArray;
import org.testng.Assert;
import org.testng.annotations.Test;


public class OAuthAPI {

	public static String access_token() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String client_id = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
		String client_secret = "erZOWM9g3UtwNRj340YYaK_W";
		String grant_type = "client_credentials";
		String scope = "trust";
		
		String respone = given()
		.formParam("client_id",client_id)
		.formParam("client_secret",client_secret)
		.formParam("grant_type",grant_type)
		.formParam("scope",scope).when()
		.post("/oauthapi/oauth2/resourceOwner/token")
		.then().extract().response().asString();
		
		return new JsonPath(respone).getString("access_token");
	}
	
	public GetCourse getCoursesResponse() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/getCourseDetails";
		GetCourse response = given().queryParam("access_token", access_token()).get().then().extract().response().as(GetCourse.class);
		return response;
	}
	
	//@Test
	public void getCoursePrice() {
		String requiredCourseName = "SoapUI Webservices testing";
		
		List<API> apiCourses = getCoursesResponse().getCourses().getApi();
		
		for(API api : apiCourses) {
			if(api.getCourseTitle().equalsIgnoreCase(requiredCourseName)) {
				String coursePrice = api.getPrice();
				System.out.println(coursePrice);
				break;
			}
		}		
	}
	
	@Test
	public void allWebAutomationCourses() {
		List<WebAutomation> webAutomationCourses = getCoursesResponse().getCourses().getWebAutomation();
		List<String> actualCourseList = new ArrayList<String>();
		List<String> expectedCourseList = List.of("Selenium Webdriver Java","Cypress","Protractor");
		
		for(WebAutomation webAuto : webAutomationCourses) {
			actualCourseList.add(webAuto.getCourseTitle());
		}
		
		Assert.assertTrue(actualCourseList.equals(expectedCourseList));

	}
	
}
