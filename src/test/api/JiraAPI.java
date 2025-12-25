package test.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

public class JiraAPI {

	public static String createIssueBody(String issueSummary) {

		return "{\r\n" + "    \"fields\": {\r\n" + "      \"project\": {\r\n" + "        \"key\": \"PANKIL\"\r\n"
				+ "      },\r\n" + "      \"summary\": \"" + issueSummary + "\",\r\n" + "      \"issuetype\": {\r\n"
				+ "        \"name\": \"Bug\"\r\n" + "      }\r\n" + "    }\r\n" + "  }";
	}

	public static String authKey() {
		return "Basic cGFua2lsc2hAZ21haWwuY29tOkFUQVRUM3hGZkdGMGNnaGRMZS1QcXFPZWdOckFGYXlSY2prZ2JqS2VtLTU5LVNsVi04TUgyQjVoVUlUbXhTT2JpbnhtZjFJSm9mS3NGM0xYU3pDTVFiWnkzT2E4Y0xxUlhfMzdEajFIcHAtNy1RM3pQQW4yTlU0VHpYU2c0WDNKTVlpQnoxYWFDenFvZkRDNXU4Z25HNGdndFlHdUg0djJVdnVhTmVwSk5wRWlXVy11SmRGb1hrcz1DNkYwOUZERA==";
	}

	public static String createIssue() {
		RestAssured.baseURI = "https://pankilsh.atlassian.net";
		String response = given().log().all().header("Content-Type", "application/json").header("Accept", "*/*")
				.header("Authorization", authKey()).body(createIssueBody("Rest created issue"))
				.post("/rest/api/3/issue").then().assertThat().statusCode(201).extract().response().asString();
		JsonPath jp = new JsonPath(response);
		return jp.getString("id");
	}

	@Test
	public void addAttachment() {
		String id = createIssue();
		RestAssured.baseURI = "https://pankilsh.atlassian.net";
		String ssPath = System.getProperty("user.dir") + "\\screenshots\\Screenshot 2025-12-23.png";

		given().header("Accept", "*/*").header("X-Atlassian-Token", "no-check").header("Authorization", authKey())
				.pathParam("id", id).multiPart(new File(ssPath)).post("/rest/api/3/issue/{id}/attachments").then()
				.assertThat().statusCode(200);

	}

}
