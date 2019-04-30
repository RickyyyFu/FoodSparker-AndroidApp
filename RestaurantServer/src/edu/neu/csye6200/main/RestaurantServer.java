package edu.neu.csye6200.main;

import java.io.*;
import java.net.*;
import java.util.*;

import edu.neu.csye6200.service.AutoUpdateInventoryService;

public class RestaurantServer {
	
	private static final int PORT = 8888;	
	// count the amount of accepted clients
	private static int count = 0;
	
	private static ServerSocket serverSocket;
	public static Socket KITCHEN_SOCKET;
	
	// storage the socket of all clients
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
//	private static ArrayList<Socket> kitchensocketList = new ArrayList<Socket>();
	
	
	public static void main(String[] args) {
		RestaurantServer server = new RestaurantServer();
		server.startServer();
	}
	
	
	public void startServer() {
		
		// update the menu every one hour
		new Thread(new AutoUpdateInventoryService()).start();
		
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("The Server has started\n");
			System.out.println("the port: " + serverSocket.getLocalPort());
			System.out.println("the local ip address: " + getLocalIP());
			System.out.println("the ip address: "); 
			getIP();
			System.out.println("");
			
			while(true) {
		
				// when a Socket request is received from the client, the server creates a Socket accordingly.
				Socket socket = serverSocket.accept();
				
//				System.out.println(socket.getInetAddress().getHostAddress());
				
//				String mark = distinguishSocket(socket);
//				if("This is kitchen".equals(mark)) {
//					KITCHEN_SOCKET = socket;
//					kitchensocketList.add(socket);
//				}
//				else {
//					customersocketList.add(socket);			
//					count++;
//					System.out.println("#" + count + " client is connected from " + socket.getInetAddress().getHostAddress() + ": " + socket.getPort());
//				}
			
				socketList.add(socket);			
				count++;
				System.out.println("#" + count + " client is connected from " + socket.getInetAddress().getHostAddress() + ": " + socket.getPort());
				
				// each time a client is connected, the ServerThread start to serve the client.
				new Thread(new ServerThread(socket)).start(); 
				
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// distinguish kitchen Socket
	private String distinguishSocket(Socket socket) {
		String mark = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mark = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mark;
	}
	
	// get local IP address
	private String getLocalIP() {
		String Localip = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			Localip = address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Localip;
	}
	
	// get IP address
	private void getIP() throws UnknownHostException, IOException {
	
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()){
		    NetworkInterface current = interfaces.nextElement();
	//		System.out.print(current + "       ");
		    if (!current.isUp() || current.isLoopback() || current.isVirtual()) 
		    	continue;
		    Enumeration<InetAddress> addresses = current.getInetAddresses();
		    while (addresses.hasMoreElements()){
		        InetAddress current_addr = addresses.nextElement();
		        if (current_addr.isLoopbackAddress()) 
		        	continue;
		        if (current_addr instanceof Inet4Address) {
		        	System.out.println("IPv4: " + current_addr.getHostAddress());
		        }
		        else if (current_addr instanceof Inet6Address) {
		        	System.out.println("IPv6: " + current_addr.getHostAddress());
		        }
		    }
		}
		
		/*	
		String IP = null;
		try {
			Enumeration enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networInterface = (NetworkInterface) enumNetworkInterfaces.nextElement();
				InetAddress inetAddress = networInterface.getInetAddresses().nextElement();
			//	IP = inetAddress.getHostAddress();
                if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() ){
            //		&& inetAddress.getHostAddress().indexOf(":") == -1) {
                	IP = inetAddress.getHostAddress();
                	break;
				}
                else {
                	IP = "null";
                }
			}
		}
		catch(SocketException e) {
			e.printStackTrace();
		}
		return IP;
		*/
		
		/*	
		Socket s = new Socket("google.com", 80);
		ystem.out.println(s.getLocalAddress().getHostAddress());
		s.close();
		*/		
	}
	
}
