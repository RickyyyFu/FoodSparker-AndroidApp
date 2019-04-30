package edu.neu.csye6200.entity;

import java.io.Serializable;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private double cost;
	private int stock;
	
	public Item() {
		
	}
	
	public Item(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
	}

}
