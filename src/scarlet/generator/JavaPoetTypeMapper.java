/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:37
 */
package scarlet.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import violet.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class JavaPoetTypeMapper {
	private JavaPoetTypeMapper() {
	}

	public static ParameterizedTypeName parameterizedTypeName(String baseTypeName, List<String> typeArguments) {
		ClassName baseClassName = ClassName.bestGuess(baseTypeName);

		TypeName[] argTypeNames = typeArguments
				.stream()
				.map(JavaPoetTypeMapper::typeName)
				.collect(Collectors.toList())
				.toArray(new TypeName[typeArguments.size()]);

		return ParameterizedTypeName.get(baseClassName, argTypeNames);
	}

	public static TypeName typeName(String typeName) {
		TypeName type;

		switch (typeName) {
			case "byte":
				type = TypeName.BYTE;
				break;
			case "short":
				type = TypeName.SHORT;
				break;
			case "int":
				type = TypeName.INT;
				break;
			case "long":
				type = TypeName.LONG;
				break;
			case "float":
				type = TypeName.FLOAT;
				break;
			case "double":
				type = TypeName.DOUBLE;
				break;
			case "char":
				type = TypeName.CHAR;
				break;
			case "boolean":
				type = TypeName.BOOLEAN;
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
