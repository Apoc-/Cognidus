/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 13:20
 */
package sphynx.generator;

public class JavaLangClassMapper {
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
