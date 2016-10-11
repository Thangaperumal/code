package com.mumms.utils;

import java.math.BigDecimal;

public class HBUtil {

	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isNotBlank(Object obj) {
		return obj != null && !isBlank(obj.toString());
	}
	
	public static String convertNumberToString(Object obj) {
		try {
			return new BigDecimal(obj.toString()).toBigInteger().toString();
		} catch (NumberFormatException nfe) {
			return obj.toString();
		}
	}

	public static String getIdFromURI(String uri) {
		if (isNotBlank(uri)) {
			int startIndex = uri.lastIndexOf("/");
			int endIndex = uri.indexOf("?");
			return uri.substring(startIndex + 1, endIndex);
		}
		return "";
	}
}