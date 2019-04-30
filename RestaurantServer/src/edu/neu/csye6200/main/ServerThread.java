package edu.neu.csye6200.main;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.neu.csye6200.entity.Customer;
import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.entity.Order;
import edu.neu.csye6200.service.ConfirmationService;
import edu.neu.csye6200.service.LoginService;
import edu.neu.csye6200.service.RegisterService;

import edu.neu.csye6200.util.InventoryUtil;
import edu.neu.csye6200.util.MsgTransfer;

import edu.neu.csye6200.util.SendMSGtoKitchen;

public class ServerThread implements Runnable{

	Socket socket;

	MsgTransfer MSG = new MsgTransfer();
	
	ServerThread(Socket socket) throws IOException{
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
			try {
				while(true) {

					// get data from client
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());	
					MSG = (MsgTransfer) ois.readObject();
					
					if(MSG.getCmd().equals("updateStock")) {
						
					}
					else {
						System.out.println(MSG.getCmd());
					}
						
					// deal with data
					if(MSG != null) {	
						execute(MSG);
					}
							
					else {
						System.out.println("The message is null from client: " + socket.getInetAddress().getHostAddress());
						MSG = new MsgTransfer();
						MSG.setResult("The message sent is null");
					}
					
					// send result to client
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(MSG);
					oos.flush();	
				}
		
		}	
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public MsgTransfer execute(MsgTransfer MSG) {
		String cmd = MSG.getCmd();

		// login service
		if(cmd.equals("Login")) {
			// Object object = (Object)MSG.getData();
			Customer customer = (Customer) MSG.getData();
			boolean flag = LoginService.login(customer);
			System.out.println(flag);
			MSG.setFlag(flag);
			if(flag) {
				MSG.setResult("login successful");
				//MSG.setSocket(socket);
				String IP = socket.getInetAddress().getHostAddress();
				MSG.setIP(IP);
			}
			else {
				MSG.setResult("login failed");
			}
		}
		
		// register service
		else if(cmd.equals("Register")) {
			Customer customer = (Customer) MSG.getData();
			boolean flag = RegisterService.register(customer);
			MSG.setFlag(flag);
			if(flag) {
				MSG.setResult("register successful");
			}
			else {
				MSG.setResult("register failed");
			}
		}
		
		
		// order submission service
		else if (cmd.equals("Submit")) {
			Customer customer = (Customer) MSG.getData();
			Map<Item,Integer> map = new HashMap<Item, Integer>();
			ArrayList<Order> order = customer.getOrderList();
			for(Order o : order) {
				map = o.getItem_QuantityMap();
				Iterator<Item> iter = map.keySet().iterator();
				  while(iter.hasNext()){
					  Item key=iter.next();
				   int value = map.get(key);
				   System.out.println(key.getName()+" "+value);
				   }
			}
			System.out.println("One order is received from " + customer.getUsername() + ": " + socket.getInetAddress().getHostAddress());	
			
			String customerIP = socket.getInetAddress().getHostAddress();
			MSG.setIP(customerIP);
			System.out.println("customerIP" + MSG.getIP());
			boolean flag = SendMSGtoKitchen.submitOrdertoKitchen(MSG);
			System.out.println(flag);
			if(flag) {
				MSG.setResult("Submission Success!\n"
						+ "Waiting for the kitchen to process the order");
			}
			else{
				MSG.setResult("Order submission failed");
			}	
		}
		
		// kitchen update stock
		else if (cmd.equals("updateStock")) {
			ArrayList<Item> inventoryList = InventoryUtil.readInventoryFromFile();
			MSG.setData(inventoryList);
		}
		
		
		
		
		// kitchen confirmation
		else if (cmd.equals("Confirm")){
			
			// get client socket
			Socket csocket = null;
			String IP = MSG.getIP();
			int num = RestaurantServer.socketList.size();		 
		    for (int index = 0; index < num; index++) {
		        Socket mSocket = RestaurantServer.socketList.get(index); 
		        String ip = mSocket.getInetAddress().getHostAddress();
		 
		        if (ip.equals(IP)) {
		        	csocket = mSocket;
		        }
		    }    
				
			//inventory status in order
			//if inventory has enough item, add 1
			ArrayList<Integer> inventoryStatus = new ArrayList<>();
	
			//check from inventory
			//get data from inventory
			ArrayList<Item> inventoryList = InventoryUtil.readInventoryFromFile();
			
			//read data from socket
			Customer customer = (Customer) MSG.getData();
			ArrayList<Order> orderList = customer.getOrderList();
			//get orders in orderList
			for(Order order : orderList){
				
				//determine if each item in an order is available
				//get item from order
				Map<Item,Integer> item_q = order.getItem_QuantityMap();
				Iterator<Item> it = item_q.keySet().iterator();	
				while(it.hasNext()) {
					Item item_order = it.next();
					//get item from inventory
					for(Item item_inventory : inventoryList) {
						//find item
						if(item_order.getName().equals(item_inventory.getName())) {
							if(item_q.get(item_order) <= item_inventory.getStock()) {
								inventoryStatus.add(1);
							}
							else{
								inventoryStatus.add(0);
							}
						}
					}
				}
				
				//determine the order status
				for(Integer status: inventoryStatus){
					//all 1, no 0 for status, whole order can be completed
					if(inventoryStatus.contains(1) && !inventoryStatus.contains(0)){
						MSG.setResult("Order has been accepted.");
						MSG.setFlag(true);
//						orderList.remove(order);
						customer.setArrayList(orderList);
						MSG.setData(customer);
/////						MSG.setCmd("finish_confirm");
						//update Inventory
						InventoryUtil.updateInventoryByOrder(order);
//						SendInventoryToKitchen.sendInventoryToKitchen(socket);
						
						MSG.setCmd("");
						
						ConfirmationService.sendMSGtoCustomer(MSG);
					}
					//all 0, no 1 for status, order should be canceled
					else if(inventoryStatus.contains(0) && !inventoryStatus.contains(1)){
						MSG.setResult("Order Canceled: no sufficient inventory.");
						MSG.setFlag(false);
						orderList.remove(order);
						customer.setArrayList(orderList);
						MSG.setData(customer);
						
						MSG.setCmd("");
						ConfirmationService.sendMSGtoCustomer(MSG);
					}
					//have 0 and 1 for status, order can be partially completed
					else{
						MSG.setResult("Order can be partially completed");
						
						//create new order
						int bq = 0;
						int cq = 0;
						int fq = 0;
						int oq = 0;
						
						ArrayList<Item> inventory =  InventoryUtil.readInventoryFromFile();
						for(Item iter : inventory) {
							if("Burger".equals(iter.getName())){
								bq = iter.getStock();
			                   }
			                   else if("Chickens".equals(iter.getName())){
			                       cq =  iter.getStock();
			                   }
			                   else if ("French Fries".equals(iter.getName())){
			                       fq =  iter.getStock();;
			                   }else if("Onion Rings".equals(iter.getName())){
			                       oq =  iter.getStock();
			                   }
						}
						
						
						Customer customer_neworder = (Customer)MSG.getData();;
						ArrayList<Order> orderlist = customer_neworder.getOrderList();
						Map<Item,Integer> map = new HashMap<Item,Integer>();
						Item chicken = new Item("Chickens",4.00);
				        Item fries = new Item("French Fries",1.50);
				        Item burger = new Item("Burger",3.50);
				        Item onion = new Item("Onion Rings",2.00);
						map.put(burger, bq);
						map.put(chicken, cq);
						map.put(fries, fq);
						map.put(onion, oq);
						Order newOrder = new Order();
						newOrder.setItem_QuantityMap(map);
						orderlist.add(newOrder);
					}
				}
	
			
				
			}
	
		}
		
		return MSG;
	}
}
