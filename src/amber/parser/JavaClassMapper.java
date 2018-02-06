/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */
package amber.parser;

import violet.Logger;

//todo: extend to all primitve types
//todo: handle references not known yet eg. a future generated class
class JavaClassMapper {
	private JavaClassMapper() {}

	public static Class className(String className) {
		switch (className) {
			case "boolean":
				return boolean.class;
			case "int":
				return int.class;
			case "float":
				return float.class;
			default:
				try {
					return Class.forName(className);
				} catch (ClassNotFoundException e) {
					Logger.console().logError(e.getMessage());
				}
				return Object.class;
		}
	}
}
