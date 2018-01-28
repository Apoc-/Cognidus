/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:37
 */
package scarlet.generator;

import com.squareup.javapoet.TypeName;

class JavaPoetTypeMapper {
	public static com.squareup.javapoet.TypeName typeName(String typeName) {
		switch (typeName) {
			case "int":
				return TypeName.INT;
			case "float":
				return TypeName.FLOAT;
			case "void":
				return TypeName.VOID;
			default:
				return com.squareup.javapoet.ClassName.bestGuess(typeName);
		}
	}
}
