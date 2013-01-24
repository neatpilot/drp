package org.cn.pilot.drp.util;

public class PLog {
	private String location;
	private String message;
	
	public PLog(String location,String message){
		location = location;
		message = message;
	}

	@Override
	public String toString() {
		return "PLog ["+location + ", {" + message + "}]";
	}
	
	
}
