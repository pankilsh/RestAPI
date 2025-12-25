package test.api;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import test.files.Payload;

public class ExcelDrivenLibrary {

	public static String baseUri = "http://216.10.245.166";

	@Test
	public void addBook() throws IOException {
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri(baseUri).setContentType(ContentType.JSON)
				.build();

		String responseString = given().spec(requestSpec).body(Payload.addBookExcel("AddBook")).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200)
				.extract().response().asString();

		String bookId = new JsonPath(responseString).getString("ID");

	}

}
