package edu.neu.csye6200.util;

import java.io.Serializable;
import java.net.Socket;

public class MsgTransfer implements Serializable{
	private static final long serialVersionUID = 1L;

//	private Socket socket;
	private String IP;
	private String cmd;
	private Object data;
	private boolean flag;
	private String result;
	
	public MsgTransfer() {
		super();
	}

	public void setIP(String IP) {
		this.IP = IP;
	}
	
	public String getIP() {
		return IP;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return result;
	}
}
