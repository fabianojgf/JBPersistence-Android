package org.jb.persistence.util;

public class EnumUtil {
	@SuppressWarnings("rawtypes")
	public static String[] descriptionValues(Enum []e) {
		String []strValues = new String[e.length];
		for (int i = 0; i < e.length; i++) {
			strValues[i] = e[i].name();
		}
		return strValues;
	}
	
	@SuppressWarnings("rawtypes")
	public static Enum descriptionValues(Enum[] e, int id) {
		return e[id];
	}
}
