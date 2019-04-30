package edu.neu.csye6200.entity;


import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String address;
	private String phone_number;
	private ArrayList<Order> orderList;
//	private Receipt receipt;

	public Customer()
	{

	}


	public Customer(String username, String password) {
		this.username = username;
		this.password = password;
		orderList = new ArrayList<Order>();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAddress(String addr) {
		this.address = addr;
	}

	public String getAddress() {
		return address;
	}

	public void setPhoneNum(String phoneNum) {
		this.phone_number = phoneNum;
	}

	public String getPhoneNum() {
		return phone_number;
	}

	public void setArrayList(ArrayList<Order> orderList){
		this.orderList = orderList;
	}

	public ArrayList<Order> getOrderList(){
		return orderList;
	}

//	public void addOrderToList(Order order) {
//		orderList.add(order);
//	}
//	
//	public void emptyOrderList() {
//		orderList.clear();
//	}

	public String toString() {
		return "username: " + this.getUsername() + ", address: " + this.getAddress() + ", phone number: " + this.getPhoneNum();
	}
}
