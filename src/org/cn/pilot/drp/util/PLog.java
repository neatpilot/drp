package org.cn.pilot.drp.util;

public class PLog {
	public static String atLocation(Object object) {
		return "       [@" + object.getClass().getName() + " ]";
	}
}
