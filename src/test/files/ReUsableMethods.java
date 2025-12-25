package test.files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

	public static JsonPath rawToJson(String rawData) {
		return new JsonPath(rawData);
	}
	
}
