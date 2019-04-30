package edu.neu.csye6200.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	private Map<Item, Integer> item_quantity;
	private double cost;
	private int status;
	
	public Order() {
		item_quantity = new HashMap<Item, Integer>();
		this.cost = 0;
		this.status = 0;
	}
	
	public Order(Map<Item, Integer> item_quantity) {
		
		this.item_quantity = item_quantity;
		
		// set cost
		double cost_items = 0;
		Iterator<Item> it = item_quantity.keySet().iterator();
		while (it.hasNext()) {
			Item item = it.next();
			cost_items += item.getCost() * item_quantity.get(item);
		}
		this.cost = cost_items + (cost_items*0.06) + (cost_items*0.3);
		
		// set status
		this.status = 0;
	}
	
	public void setItem_QuantityMap(Map<Item, Integer> item_quantity) {
		this.item_quantity = item_quantity;
	}
	
	public Map<Item, Integer> getItem_QuantityMap(){
		return item_quantity;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setCost(Map<Item, Integer> item_quantity) {
		double cost_items = 0;
		Iterator<Item> it = item_quantity.keySet().iterator();
		while (it.hasNext()) {
			Item item = it.next();
			cost_items += item.getCost() * item_quantity.get(item);
		}
		this.cost = cost_items + (cost_items*0.06) + (cost_items*0.3);
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
