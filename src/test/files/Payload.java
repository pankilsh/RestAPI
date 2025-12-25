package test.files;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
				+ "\"aisle\":\"227\",\r\n" + "\"author\":\"Pankil\"\r\n" + "}\r\n" + "";
	}

	public static String addBookStatic() {
		return "{\r\n" + "\"name\":\"Learn Appium Automation with Java 2\",\r\n" + "\"isbn\":\"qwert\",\r\n"
				+ "\"aisle\":\"111\",\r\n" + "\"author\":\"Pankil\"\r\n" + "}";
	}

	public static HashMap<String, Object> addBookMap() {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("name", "Learn Appium Automation with Java 2");
		jsonMap.put("isbn", "qwert");
		jsonMap.put("aisle", "111");
		jsonMap.put("author", "Pankil");

		return jsonMap;
	}

	public static HashMap<String, Object> addBookExcel(String testDataName) throws IOException {
		String excelPath = "C:\\Users\\PS100160\\eclipse-workspace\\RestAPIProject\\JsonFiles\\ExcelData.xlsx";
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
		XSSFSheet sheets = null;
		DataFormatter formatter = new DataFormatter();

		int numberOfSheets = workbook.getNumberOfSheets();

		for (int i = 0; i < numberOfSheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("AddBook")) {
				sheets = workbook.getSheetAt(i);
				break;
			}
		}

		int numberOfRows = sheets.getLastRowNum();
		
		for (int i = 1; i <= numberOfRows; i++) {
			String key = formatter.formatCellValue(sheets.getRow(i).getCell(0));
			String value = formatter.formatCellValue(sheets.getRow(i).getCell(1));
			jsonMap.put(key, value);
		}

		return jsonMap;
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
