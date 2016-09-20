package com.att.nod.core.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NODUtilities {

	private NODUtilities() {
	}

	public static int getIntValue(Integer value) {
		int intValue = 0;
		if (value != null) {
			intValue = value.intValue();
		}
		return intValue;
	}

	public static String removeNull(String str) {
		if (str == null) {
			return ""; //$NON-NLS-1$
		} else {
			return str.trim();
		}
	}

	public static String trim(String str) {
		if (str == null) {
			return null;
		} else {
			return str.trim();
		}
	}

	public static boolean isNullOrEmpty(String string) {
		if ((string == null) || "".equals(string.trim())) { //$NON-NLS-1$
			return true;
		} else {
			return false;
		}

	}

	public static String getMessage(String message, Map values) {
		StringBuffer buffer = new StringBuffer(message);
		Iterator itr = values.keySet().iterator();
		boolean first = true;
		while (itr.hasNext()) {
			String key = (String) itr.next();
			String value = (String) values.get(key);
			if (first) {
				first = false;
			} else {
				buffer.append(',');
			}
			buffer.append(key);
			buffer.append('=');
			buffer.append(value);
		}
		return buffer.toString();
	}

	public static String concatKeys(List values) {
		Iterator itr = values.iterator();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; itr.hasNext(); i++) {
			sb.append(itr.next());
			if (i < values.size()) {
				sb.append('~');
			}
		}
		return sb.toString();
	}

	public static Date getSQLDate(Timestamp timestamp) {
		Date date = null;
		if (timestamp != null) {
			date = new Date(timestamp.getTime());
		}
		return date;
	}

}
