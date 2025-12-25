package test.files;

import java.util.List;

import googlemapsapi.pojo.AddPlace;
import googlemapsapi.pojo.Location;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.locals_return;

public class Payload {

	public static String addPlaceJson() {
		return "{\r\n" + "	\"location\":{\r\n" + "    	\"lat\" : -38.383494, \r\n" + "    	\"lng\" : 33.427362\r\n"
				+ "	},\r\n" + "	\"accuracy\":50,\r\n" + "	\"name\":\"Frontline house\",\r\n"
				+ "	\"phone_number\":\"(+91) 983 893 3937\",\r\n"
				+ "	\"address\" : \"29, side layout, cohen 09\",\r\n" + "	\"types\": [\"shoe park\",\"shop\"],\r\n"
				+ "	\"website\" : \"http://google.com\",\r\n" + "	\"language\" : \"French-IN\"\r\n" + "}";
	}

	public static String updateAddress(String place_id) {
		return "{\r\n" + "\"place_id\":\"" + place_id + "\",\r\n" + "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "";
	}

	public static String complexJason() {
		return "{\r\n" + "\"dashboard\": {\r\n" + "\"purchaseAmount\": 910,\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n" + "},\r\n" + "\"courses\": [\r\n" + "{\r\n"
				+ "\"title\": \"Selenium Python\",\r\n" + "\"price\": 50,\r\n" + "\"copies\": 6\r\n" + "},\r\n"
				+ "{\r\n" + "\"title\": \"Cypress\",\r\n" + "\"price\": 40,\r\n" + "\"copies\": 4\r\n" + "},\r\n"
				+ "{\r\n" + "\"title\": \"RPA\",\r\n" + "\"price\": 45,\r\n" + "\"copies\": 10\r\n" + "}\r\n" + "]\r\n"
				+ "}";
	}

	public static String addBook(String bookName, String id) {
		return "{\r\n" + "\"name\":\"" + bookName + "\",\r\n" + "\"isbn\":\"" + id + "\",\r\n"
				+ "\"aisle\":\"227\",\r\n" + "\"author\":\"John foe\"\r\n" + "}\r\n" + "";
	}

	public static AddPlace addPlaceObject() {

		Location loc = new Location();
		loc.setLat("-38.383494");
		loc.setLng("33.427362");

		AddPlace addPlace = new AddPlace();
		addPlace.setLocation(loc);
		addPlace.setAccuracy(50);
		addPlace.setName("Frontline house");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("29, side layout, cohen 09");
		addPlace.setTypes(List.of("shoe park", "shop"));
		addPlace.setWebsite("http://google.com");
		addPlace.setLanguage("French-IN");
		
		return addPlace;

	}

}
