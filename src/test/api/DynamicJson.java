package test.api;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import test.files.Payload;
import test.files.ReUsableMethods;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DynamicJson {

	@DataProvider(name = "addBooksData")
	public Object[][] addBookData() {

		return new Object[][] { { "Book1", "id1" }, { "Book2", "id2" } };
	}

	@Test(dataProvider = "addBooksData")
	public void addBook(String bookName, String bookId) {

		RestAssured.baseURI = "http://216.10.245.166";

		String addBookResponse = given().header("Content-Type", "application/Json")
				.body(Payload.addBook(bookName, bookId)).post("/Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().response().asString();

		String id = ReUsableMethods.rawToJson(addBookResponse).get("ID");

		System.out.println(id);
	}

	@Test
	public void addBookTwo() throws IOException {

		RestAssured.baseURI = "http://216.10.245.166";
		
		String addBookResponse = given().log().all().header("Content-Type", "application/Json")
				.body(new String(Files
						.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\JsonFiles\\AddBooksJson.json"))))
				.when().post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response()
				.asString();

		String id = ReUsableMethods.rawToJson(addBookResponse).get("ID");

		System.out.println(id);

	}

}
