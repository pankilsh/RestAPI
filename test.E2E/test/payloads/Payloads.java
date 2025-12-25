package test.payloads;

import java.util.ArrayList;
import java.util.List;

import test.pojo.CreateOrder;
import test.pojo.LoginRequest;
import test.pojo.OrdersDetails;

public class Payloads {
	
	public static LoginRequest loginCredentials() {
		LoginRequest login = new LoginRequest();
		login.setUserEmail("pankil@gmail.com");
		login.setUserPassword("Pass@12345");
		
		return login;
	}
	
	public static CreateOrder createNewOrder(List<String> productOrderedId) {
		List<OrdersDetails> orderList = new ArrayList<OrdersDetails>();
		
		for(String prodId : productOrderedId) {
			OrdersDetails orderDetails = new OrdersDetails();
			orderDetails.setCountry("India");
			orderDetails.setProductOrderedId(prodId);
			orderList.add(orderDetails);
		}
		
		CreateOrder createOrders = new CreateOrder();
		createOrders.setOrders(orderList);
		
		return createOrders;
	}

}
