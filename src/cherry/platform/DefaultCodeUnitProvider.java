/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 09.02.18 18:50
 */

package cherry.platform;

import cherry.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class DefaultCodeUnitProvider {
	private DefaultCodeUnitProvider() {}

	public static List<CodeUnit> createDefaultMethodCodeUnits(CodeUnit classCodeUnit) {
		List<CodeUnit> methodCodeUnits = new LinkedList<>();

		//check only fields and generate getter and/or setter if neccessary
		classCodeUnit
				.getSubCodeUnits()
				.stream()
				.filter(cu -> cu.getType() == CodeUnitType.FIELD)
				.forEach(cu -> {
					String typeName = (String) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
					String ident = (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();

					if(cu.getData().containsKey(CodeUnitDatumType.GETTER)) {
						methodCodeUnits.add(createGetterCodeUnit(ident, typeName));
					}

					if (cu.getData().containsKey(CodeUnitDatumType.SETTER)) {
						methodCodeUnits.add(createSetterCodeUnit(ident, typeName));
					}
				});

		return methodCodeUnits;
	}

	public static CodeUnit createGetterCodeUnit(String identifier, String typeName) {
		CodeUnit method = createMethodCodeUnit(identifier, "get", typeName);
		CodeUnit methodBody = createMethodBodyCodeUnit("return " + identifier + ";");

		method.addSubCodeUnit(methodBody);

		return method;
	}

	public static CodeUnit createSetterCodeUnit(String identifier, String typeName) {
		CodeUnit method = createMethodCodeUnit(identifier, "set", void.class.getTypeName());

		CodeUnit methodParameters = createMethodParameterCodeUnit("value", typeName);
		CodeUnit methodBody = createMethodBodyCodeUnit("this." + identifier + " = value;");

		method.addSubCodeUnit(methodParameters);
		method.addSubCodeUnit(methodBody);

		return method;
	}

	private static CodeUnit createMethodCodeUnit(String identifier, String set, String typeName) {
		return CodeUnitBuilder
				.createWithIdentifier(set + StringUtils.capitalize(identifier))
				.setCodeUnitType(CodeUnitType.METHOD)
				.withModifiers(CodeUnitModifier.PUBLIC)
				.withReturnType(typeName)
				.end();
	}

	public static CodeUnit createMethodParameterCodeUnit(String identifier, String typeName) {
		return CodeUnitBuilder
				.createWithIdentifier(identifier)
				.setCodeUnitType(CodeUnitType.METHOD_PARAM)
				.withDataType(typeName)
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
