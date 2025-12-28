package test.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import test.pojo.GetCourse;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class OAuthCode {

	public void authCodeServer() {

		RestAssured.baseURI = "https://accounts.google.com";

		HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("scope", "https://www.googleapis.com/auth/userinfo.email");
		queryMap.put("auth_url", "https://accounts.google.com/o/oauth2/v2/auth");
		queryMap.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		queryMap.put("response_type", "code");
		queryMap.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");

		given().queryParams(queryMap).when().get("/o/oauth2/v2/auth");
	}

	public String authCode() {
		authCodeServer();
		String url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.findElement(By.cssSelector("input#identifierId")).sendKeys("userid");
		driver.findElement(By.cssSelector("input#identifierId")).sendKeys(Keys.ENTER);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("password");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		
		String currentURL = driver.getCurrentUrl();
		String authCode = currentURL.split("code=")[1].split("&scope")[0];
		
		return authCode;
	}


	public String access_token() {
		RestAssured.baseURI = "https://www.googleapis.com";

		HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("code", authCode());
		queryMap.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		queryMap.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		queryMap.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");
		queryMap.put("grant_type", "authorization_code");
		queryMap.put("scope", "https://www.googleapis.com/auth/userinfo.email");
		queryMap.put("response_type", "code");

		String response = given().urlEncodingEnabled(false).queryParams(queryMap).when().post("/oauth2/v4/token").then().extract().response()
				.asString();

		String access_token = new JsonPath(response).getString("access_token");

		return access_token;

	}

	@Test
	public void getCourses() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		GetCourse response = given().log().all().queryParam("access_token", access_token()).when().get("/getCourse.php")
				.then().log().all().extract().response().as(GetCourse.class);

		System.out.println(response.getCourses().getApi().get(0).getCourseTitle());
	}

}
