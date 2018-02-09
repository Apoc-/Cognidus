/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 31.01.18 14:33
 */

package cherry.model;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class CodeUnitBuilderUtils {
	private CodeUnitBuilderUtils() {}

	public static List<CodeUnit> createDefaultMethodCodeUnits(CodeUnit classCodeUnit) {
		List<CodeUnit> methodCodeUnits = new LinkedList<>();

		//check only fields and generate getter and/or setter if neccessary
		classCodeUnit
				.getSubCodeUnits()
				.stream()
				.filter(cu -> cu.getType() == CodeUnitType.FIELD)
				.forEach(cu -> {
					Class type = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
					String ident = (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();

					if(cu.getData().containsKey(CodeUnitDatumType.GETTER)) {
						methodCodeUnits.add(createGetterCodeUnit(ident, type));
					}

					if (cu.getData().containsKey(CodeUnitDatumType.SETTER)) {
						methodCodeUnits.add(createSetterCodeUnit(ident, type));
					}
				});

		return methodCodeUnits;
	}

	public static CodeUnit createGetterCodeUnit(String identifier, Class type) {
		CodeUnit method = createMethodCodeUnit(identifier, "get", type);
		CodeUnit methodBody = createMethodBodyCodeUnit("return " + identifier + ";");

		method.addSubCodeUnit(methodBody);

		return method;
	}

	public static CodeUnit createSetterCodeUnit(String identifier, Class type) {
		CodeUnit method = createMethodCodeUnit(identifier, "set", void.class);

		CodeUnit methodParameters = createMethodParameterCodeUnit("value", type);
		CodeUnit methodBody = createMethodBodyCodeUnit("this." + identifier + " = value;");

		method.addSubCodeUnit(methodParameters);
		method.addSubCodeUnit(methodBody);

		return method;
	}

	private static CodeUnit createMethodCodeUnit(String identifier, String set, Class type) {
		return CodeUnitBuilder
				.createWithIdentifier(set + StringUtils.capitalize(identifier))
				.setCodeUnitType(CodeUnitType.METHOD)
				.withModifiers(CodeUnitModifier.PUBLIC)
				.withReturnType(type)
				.end();
	}

	public static CodeUnit createMethodParameterCodeUnit(String identifier, Class type) {
		return CodeUnitBuilder
				.createWithIdentifier(identifier)
				.setCodeUnitType(CodeUnitType.METHOD_PARAM)
				.withDataType(type)
				.end();
	}

	public static CodeUnit createMethodBodyCodeUnit(String body) {
		return CodeUnitBuilder
				.create()
				.setCodeUnitType(CodeUnitType.METHOD_BODY)
				.withMethodBody(body)
				.end();
	}
}
