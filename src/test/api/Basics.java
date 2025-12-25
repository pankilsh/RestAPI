package test.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import test.files.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

public class Basics {

	public void addPlaceAPI() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlaceJson()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).extract().response().asString();

		JsonPath jp = new JsonPath(response);
		String place_id = jp.getString("place_id");

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.updateAddress(place_id)).when().put("maps/api/place/update/json").then().log().all()
				.assertThat().body("msg", equalTo("Address successfully updated"));

		Map<String, Object> map = new HashMap();
		map.put("key", "qaclick123");
		map.put("place_id", place_id);

		// given().log().all().queryParams(map).when().get("maps/api/place/get/json").then().log().all().assertThat()
		// .body("address", equalTo("70 Summer walk, USA"));

		given().log().all().queryParams("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("maps/api/place/get/json").then().log().all().assertThat()
				.body("address", equalTo("70 Summer walk, USA"));

	}

	public void addPlaceAPITwo() {

		RequestSpecification specRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();

		ResponseSpecification specResponse = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		String response = given().spec(specRequest).body(Payload.addPlaceObject()).when().post("maps/api/place/add/json").then()
				.spec(specResponse).extract().response().asString();
		
		System.out.println(response);

	}

	public void complexJsonParse() {
		String jsonContent = Payload.complexJason();

		JsonPath jp = new JsonPath(jsonContent);

		int coursesSize = jp.getInt("courses.size()");

		for (int i = 0; i < coursesSize; i++) {
			System.out.println(jp.getString("courses[" + i + "].title"));
		}

		for (int i = 0; i < coursesSize; i++) {
			String courseName = jp.getString("courses[" + i + "].title");
			if (courseName.equalsIgnoreCase("RPA")) {
				System.out.println("Copies sold : " + jp.getString("courses[" + i + "].copies"));
				break;
			}
		}

		int dashboardPrice = jp.getInt("dashboard.purchaseAmount");
		int totalPrice = 0;

		for (int i = 0; i < coursesSize; i++) {
			int price = jp.getInt("courses[" + i + "].price");
			int copies = jp.getInt("courses[" + i + "].copies");
			totalPrice = totalPrice + (price * copies);
		}

		System.out.println(totalPrice);

		Assert.assertTrue(dashboardPrice == totalPrice);
	}

	public void addPlaceSerialized() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		given().log().all().header("Content-Type", "application/json").queryParam("key", "qaclick123")
				.body(Payload.addPlaceObject()).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200);
	}

	public static void main(String[] args) {
		Basics obj = new Basics();

		// obj.addPlaceAPI();
		// obj.complexJsonParse();
		obj.addPlaceSerialized();
	}

}
