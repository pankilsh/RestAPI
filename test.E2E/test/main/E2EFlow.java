package test.main;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import test.payloads.Payloads;
import test.pojo.AddProductResponse;
import test.pojo.LoginResponse;

public class E2EFlow {
	public static String baseUri = "https://rahulshettyacademy.com/";

	public LoginResponse loginToApp() {
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri(baseUri).setContentType(ContentType.JSON)
				.build();

		LoginResponse login = given().spec(requestSpec).body(Payloads.loginCredentials()).when()
				.post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);

		return login;
	}

	@Test
	public void addNewProduct() {

		LoginResponse loginResonse = loginToApp();
		String access_token = loginResonse.getToken();
		String user_id = loginResonse.getUserId();

		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri(baseUri)
				.addHeader("Authorization", access_token).build();

		Map<String, Object> mapParamaters = new HashMap<String, Object>();
		mapParamaters.put("productName", "qwerty");
		mapParamaters.put("productAddedBy", user_id);
		mapParamaters.put("productCategory", "fashion");
		mapParamaters.put("productSubCategory", "shirts");
		mapParamaters.put("productPrice", "1150");
		mapParamaters.put("productDescription", "Pankil's Product");
		mapParamaters.put("productFor", "women");

		AddProductResponse addproduct = given().relaxedHTTPSValidation().spec(requestSpec).params(mapParamaters)
				.multiPart("productImage",
						new File(System.getProperty("user.dir") + "//screenshots//Screenshot 2025-12-23.png"))
				.when().post("/api/ecom/product/add-product").then().extract().response().as(AddProductResponse.class);

		List<String> prodIds = new ArrayList<String>();
		prodIds.add(addproduct.getProductId());

		RequestSpecification requestSpecOrders = new RequestSpecBuilder().setBaseUri(baseUri)
				.addHeader("Authorization", access_token).setContentType(ContentType.JSON).build();

		given().relaxedHTTPSValidation().spec(requestSpecOrders).body(Payloads.createNewOrder(prodIds)).when()
				.post("/api/ecom/order/create-order").then().extract().response().asString();
		
		given().relaxedHTTPSValidation().spec(requestSpec).pathParam("prodId", prodIds.get(0)).when().delete("/api/ecom/product/delete-product/{prodId}").then().log().all();

	}

}
