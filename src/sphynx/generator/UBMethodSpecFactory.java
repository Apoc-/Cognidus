/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 19:44
 */

package sphynx.generator;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitModifier;

import javax.lang.model.element.Modifier;

public class UBMethodSpecFactory {
	private UBMethodSpecFactory() { }

	public static MethodSpec createForType(UBMethodSpec ubMethodSpec, TypeName builderType) {
		MethodSpec createdMethodSpec = null;

		switch(ubMethodSpec) {
			case CREATE_WITH_IDENTIFIER:
				createdMethodSpec = createCreateWithIdSpec(ubMethodSpec.toString(), builderType);
				break;
			case WITH_DATA_TYPE:
				createdMethodSpec = createWithDataTypeSpec(ubMethodSpec.toString(), builderType);
				break;
			case WITH_MODIFIERS:
				createdMethodSpec = createWithModsSpec(ubMethodSpec.toString(), builderType);
				break;
			case WITH_SUB_CODEUNIT:
				createdMethodSpec = createWithSubCodeUnitSpec(ubMethodSpec.toString(), builderType);
				break;
			case END:
				createdMethodSpec = createEndSpec(ubMethodSpec.toString());
				break;
		}

		return createdMethodSpec;
	}

	private static MethodSpec createCreateWithIdSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(builderType)
				.addParameter(String.class, "identifier")
				.addStatement("$T cub = new $T()", builderType, builderType)
				.addStatement("cub.codeUnit.addCodeUnitDatum($S, identifier)", "identifier")
				.addStatement("return cub")
				.build();
	}

	private static MethodSpec createWithModsSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(ArrayTypeName.get(CodeUnitModifier[].class), "modifiers")
				.varargs()
				.addStatement("this.codeUnit.addCodeUnitDatum($S, modifiers)", "modifier")
				.addStatement("return this")
				.build();
	}

	private static MethodSpec createWithDataTypeSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(Class.class, "dataType")
				.addStatement("this.codeUnit.addCodeUnitDatum($S, dataType)", "dataType")
				.addStatement("return this")
				.build();
	}

	private static MethodSpec createWithSubCodeUnitSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(CodeUnit.class, "codeUnit")
				.addStatement("this.codeUnit.addSubCodeUnit(codeUnit)")
				.addStatement("return this")
				.build();
	}

	private static MethodSpec createEndSpec(String identifier) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(CodeUnit.class)
				.addStatement("return codeUnit")
				.build();
	}


}
