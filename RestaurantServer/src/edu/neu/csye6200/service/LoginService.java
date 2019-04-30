package edu.neu.csye6200.service;

import java.io.*;
import edu.neu.csye6200.entity.Customer;

public class LoginService {

	public static boolean login(Customer customer) {
		String filename = "/Users/curlyfu/eclipse-workspace/RestaurantServer/Customer.txt";
		String thisLine = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename));){
			while((thisLine = br.readLine()) != null) {
				String[] customer_info = thisLine.split(",");
				
//				for(String str : customer_info) {
//					System.out.println(str);
//				}
				
				if(customer_info[0].equals(customer.getUsername()) && customer_info[1].equals(customer.getPassword())) {
					br.close();
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
