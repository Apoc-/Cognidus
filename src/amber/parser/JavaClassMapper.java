/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */
package amber.parser;

class JavaClassMapper {
	public static Class className(String className) {
		switch (className) {
			case "int":
				return int.class;
			case "float":
				return float.class;
			default:
				try {
					return Class.forName(className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return Object.class;
		}
	}
}
