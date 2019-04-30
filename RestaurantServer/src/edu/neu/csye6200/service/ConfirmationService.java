package edu.neu.csye6200.service;

import edu.neu.csye6200.main.RestaurantServer;
import edu.neu.csye6200.util.MsgTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConfirmationService {

    private static Socket CUSTOMER_SOCKET;
    static int count = 0;

    public static boolean sendMSGtoCustomer(MsgTransfer MSG) {
        try {
      //  	String IP = MSG.getIP();
        	String IP = "10.110.24.185";
        	System.out.println(IP);
        	
        	
        	int num = RestaurantServer.socketList.size();	
		    for (int index = 0; index < num; index++) {
		        Socket mSocket = RestaurantServer.socketList.get(index); 
//		        System.out.println("mSocket: " + mSocket.getInetAddress().getHostAddress());
		        String ip = mSocket.getInetAddress().getHostAddress();
		        
		        if (ip.equals(IP)) {
		        	
		        	CUSTOMER_SOCKET = mSocket;
		        	System.out.println("Csocket: " + CUSTOMER_SOCKET.getInetAddress().getHostAddress());
		        	count++;
		        	System.out.println(count);
		        }
		    }   
        	
        	
            ObjectOutputStream oosc_customer = new ObjectOutputStream(CUSTOMER_SOCKET.getOutputStream());
   //         ObjectInputStream oisc_customer = new ObjectInputStream(CUSTOMER_SOCKET.getInputStream());

            oosc_customer.writeObject(MSG);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
