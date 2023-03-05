package com.toni.utils;

public class StringUtils {
	private static StringUtils INSTANCE;
	
	private StringUtils() {
		
	}
	
	public static StringUtils getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new StringUtils();
		}
		return INSTANCE;
	}
	
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}
	
	public static boolean isEmpty(String string) {
		if(string == null || "".equals(string)) {
			return true;
		}
		return false;
	}
}
