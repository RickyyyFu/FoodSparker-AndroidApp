package edu.neu.csye6200.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import edu.neu.csye6200.entity.Item;
import edu.neu.csye6200.entity.Order;

// the method to modify inventory.txt file should be synchronized
public class InventoryUtil {
	private static String filename = "/Users/curlyfu/eclipse-workspace/RestaurantServer/Inventory.txt";	
	
	public static ArrayList<Item> readInventoryFromFile() {
		ArrayList<Item> inventoryList = new ArrayList<Item>();
		String thisLine = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename));){
			while((thisLine = br.readLine()) != null) {
				String[] item_inventory = thisLine.split(",");
				
				Item item = new Item();
				item.setName(item_inventory[0]);
				item.setCost(Double.parseDouble(item_inventory[1]));
				item.setStock(Integer.parseInt(item_inventory[2]));
				
				inventoryList.add(item);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inventoryList;
	}
	
	
	public static synchronized void writeInventoryToFile(ArrayList<Item> inventoryList) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		
			for(Item item : inventoryList) {
				String item_toString = item.getName() + "," + item.getCost() + "," + item.getStock();
				bw.write(item_toString);
				bw.newLine();
			} 
			
			bw.flush();
			bw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static synchronized void updateInventoryByOrder(Order order){
		ArrayList<Item> inventoryList = readInventoryFromFile();
		Map<Item, Integer> item_quantity = order.getItem_QuantityMap();
		Iterator<Item> it = item_quantity.keySet().iterator();
		
		while(it.hasNext()) {
			Item item_order = it.next();
			
			for(Item item_inventory : inventoryList) {
				if(item_order.getName().equals(item_inventory.getName())) {
					int current_inventory = item_inventory.getStock() - item_order.getStock();
					item_inventory.setStock(current_inventory);
				}
			}
		}
		
		writeInventoryToFile(inventoryList);
	}
	
	/*
	public static ArrayList<Item> updateIventoryByOrder(ArrayList<Item> inventoryList, Order order){
		Map<Item, Integer> item_quantity = order.getItem_QuantityMap();
		Iterator<Item> it = item_quantity.keySet().iterator();
		
		while(it.hasNext()) {
			Item item_order = it.next();
			
			for(Item item_inventory : inventoryList) {
				if(item_order.getName().equals(item_inventory.getName())) {
					int current_inventory = item_inventory.getInventory() - item_order.getInventory();
					item_inventory.setInventory(current_inventory);
				}
			}
		}
		
		return inventoryList;
	}
	*/
	
	public static synchronized void autoUpdateIventory() {
		ArrayList<Item> inventoryList = readInventoryFromFile();
		
		for(Item item : inventoryList) {
			int current_inventory = item.getStock() + 50;
			item.setStock(current_inventory);
		}
		writeInventoryToFile(inventoryList);			
	}
	
	public static void sendIventoryToKitchen() {
		
	}
	
	/*
	public void produceItem(String name, int quantity) {
		
	}
	
	public void consumeItem(String name, int quantity) {
		
	}
		
	
	public void addOneItemToFile(Item item, int quantity) {
		ArrayList<Item> inventoryList = readIventoryFromFile();
		
		for(Item item_file : inventoryList) {	
			if(item.getName().equals(item_file.getName()){
				produceItem(item.getName(), quantity);
				return;
			}
		}
				
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
				
			String[] item_toString = item.getName()
			bw.write(item_toString);
			bw.newLine();
			
			bw.flush();
			bw.close();			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void deleteOneItemInFile(Item item) {
		
	}
	*/
}
