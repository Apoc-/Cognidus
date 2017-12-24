/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:37
 */
package wolpertinger.generator;

public class JavaPoetTypeMapper {
	public static com.squareup.javapoet.TypeName typeName(String typeName) {
		switch (typeName) {
			case "int":
				return com.squareup.javapoet.TypeName.INT;
			case "float":
				return com.squareup.javapoet.TypeName.FLOAT;
			default:
				return com.squareup.javapoet.ClassName.bestGuess(typeName);
		}
	}
}
