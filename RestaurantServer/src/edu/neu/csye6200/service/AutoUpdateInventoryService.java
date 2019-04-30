package edu.neu.csye6200.service;

import edu.neu.csye6200.util.InventoryUtil;
import edu.neu.csye6200.util.SendMSGtoKitchen;

public class AutoUpdateInventoryService implements Runnable{

	@Override
	public void run() {
		while(true) {
			try {
//				SendMSGtoKitchen.sendInventoryToKitchen();
				Thread.sleep(10000);
		//		Thread.sleep(1000);
				InventoryUtil.autoUpdateIventory();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}
