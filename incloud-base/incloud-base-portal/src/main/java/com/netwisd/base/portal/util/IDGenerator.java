package com.netwisd.base.portal.util;

import java.util.Date;
import java.util.UUID;

public class IDGenerator {

	public static String generateID() {
		return generateID(System.currentTimeMillis());
	}

	public static String generateID(Date date) {
		return generateID(date.getTime());
	}


	public static String generateID(long time) {
		String rtnVal = Long.toHexString(time);
		rtnVal += UUID.randomUUID();
		rtnVal = rtnVal.replaceAll("-", "");
		return rtnVal.substring(0, 32);
	}

	protected static void printIDTime(String id) {
		String timeInfo = id.substring(0, 11);
	}


	public static Date getIDCreateTime(String id) {
		String timeInfo = id.substring(0, 11);
		return new Date(Long.parseLong(timeInfo, 16));
	}
}
