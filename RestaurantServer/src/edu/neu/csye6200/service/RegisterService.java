package edu.neu.csye6200.service;

import java.io.*;

import edu.neu.csye6200.entity.Customer;

public class RegisterService {
	
	public static boolean register(Customer customer) {
		String filename = "/Users/curlyfu/eclipse-workspace/RestaurantServer/Customer.txt";
		String thisLine = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename));){
			
			while((thisLine = br.readLine()) != null) {
				String[] customer_info = thisLine.split(",");
				if(customer_info[0].equals(customer.getUsername())) {
					br.close();
					return false;
				}
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
			String customer_toString = customer.getUsername() + "," + customer.getPassword();
			bw.write(customer_toString);
			bw.newLine();
			
			br.close();
			bw.flush();
			bw.close();
			
			return true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
