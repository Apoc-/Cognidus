/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:37
 */
package scarlet.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import violet.Logger;

class JavaPoetTypeMapper {
	private JavaPoetTypeMapper() {}

	public static com.squareup.javapoet.TypeName typeName(String typeName) {
		TypeName type;

		switch (typeName) {
			case "int":
				type = TypeName.INT;
				break;
			case "float":
				type = TypeName.FLOAT;
				break;
			case "void":
				type = TypeName.VOID;
				break;
			default:
				try {
					type = com.squareup.javapoet.ClassName.bestGuess(typeName);
				} catch (IllegalArgumentException ex) {
					Logger.console().logError("IllegalArgumentException in JavaPoetTypeMapper, falling back on default TypeName");
					type = getDefaultTypeName(typeName);
				}
		}

		return type;
	}

	private static TypeName getDefaultTypeName(String typeName) {
		return ClassName.get("", typeName);
	}
}
