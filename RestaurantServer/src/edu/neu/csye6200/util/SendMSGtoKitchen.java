package edu.neu.csye6200.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.main.RestaurantServer;

public class SendMSGtoKitchen {

	private static final String KITCHEN_IP = "127.0.0.1";
	private static Socket ksocket;
	
	public SendMSGtoKitchen() {
//		System.out.println("constructor K");
//		int num = RestaurantServer.socketList.size();	
//	    for (int index = 0; index < num; index++) {
//	        Socket mSocket = RestaurantServer.socketList.get(index); 
//	        System.out.println(mSocket.getInetAddress().getHostAddress());
//	        String ip = mSocket.getInetAddress().getHostAddress();
//	        
//	        if (ip.equals(KITCHEN_IP)) {
//	        	System.out.println(mSocket.getInetAddress().getHostAddress());
//	        	SendMSGtoKitchen.ksocket = mSocket;
//	        }
//	    }   
	}
	
//	public SubmitOrderService(Socket KITCHENSOCKET) {
//		this.KITCHENSOCKET = KITCHENSOCKET;
//	}
	
	public static boolean submitOrdertoKitchen(MsgTransfer MSG) {	
		try { 
			int num = RestaurantServer.socketList.size();	
		    for (int index = 0; index < num; index++) {
		        Socket mSocket = RestaurantServer.socketList.get(index); 
//		        System.out.println("mSocket: " + mSocket.getInetAddress().getHostAddress());
		        String ip = mSocket.getInetAddress().getHostAddress();
		        
		        if (ip.equals(KITCHEN_IP)) {
		        	
		        	ksocket = mSocket;
//		        	System.out.println("ksocket" + ksocket.getInetAddress().getHostAddress());
		        }
		    }   
			
			System.out.println("use ksocket: " + ksocket.getInetAddress().getHostAddress() + ":" + ksocket.getPort());
			ObjectOutputStream oos_kithcen = new ObjectOutputStream(ksocket.getOutputStream());
			oos_kithcen.writeObject(MSG);			
//？？？？			ObjectInputStream ois_kithcen = new ObjectInputStream(ksocket.getInputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void sendMSGtoKitchen(MsgTransfer MSG) {	
		try {
			int num = RestaurantServer.socketList.size();	
		    for (int index = 0; index < num; index++) {
		        Socket mSocket = RestaurantServer.socketList.get(index); 
		        String ip = mSocket.getInetAddress().getHostAddress();
		        
		        if (ip.equals(KITCHEN_IP)) {
		        	System.out.println(mSocket.getInetAddress().getHostAddress());
		        	SendMSGtoKitchen.ksocket = mSocket;
		        }
		    }   
		    ObjectOutputStream oos_kithcen = new ObjectOutputStream(ksocket.getOutputStream());
		    oos_kithcen.writeObject(MSG);
//			ObjectInputStream ois_kithcen = new ObjectInputStream(ksocket.getInputStream());
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendInventoryToKitchen() {
		ArrayList<Item> inventoryList = InventoryUtil.readInventoryFromFile();
		
		MsgTransfer MSG_STOCK = new MsgTransfer();
		MSG_STOCK.setData(inventoryList);
		SendMSGtoKitchen.sendMSGtoKitchen(MSG_STOCK);
	}
}
