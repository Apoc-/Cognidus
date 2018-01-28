/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:09
 */

package jade;

import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitModifier;
import cherry.model.CodeUnitType;
import scarlet.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeUnitTransformator {
	public JavaClass transformClassCodeUnit(CodeUnit cu) {
		JavaClass clazz = new JavaClass();

		clazz.name = this.transformIdentifier(cu);
		clazz.modifiers = this.transformModifier(cu);
		clazz.fields = this.transformFields(cu);
		clazz.methods = this.transformMethods(cu);

		return clazz;
	}

	private String transformIdentifier(CodeUnit cu) {
		return (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();
	}


	private Set<JavaModifier> transformModifier(CodeUnit cu) {
		CodeUnitModifier[] cm = (CodeUnitModifier[]) cu.getCodeUnitDatum(CodeUnitDatumType.MODIFIER).getDatumData();
		return Arrays
				.stream(cm)
				.map(m -> JavaModifier.valueOf(m.name()))
				.collect(Collectors.toSet());
	}

	private List<JavaField> transformFields(CodeUnit cu) {
		 return cu.getSubCodeUnits()
				.stream()
				.filter(unit -> unit.getType() == CodeUnitType.FIELD)
				.map(this::transformField)
				.collect(Collectors.toList());
	}

	private JavaField transformField(CodeUnit cu) {
		JavaField jField = new JavaField();
		jField.modifiers = this.transformModifier(cu);
		jField.identifier = this.transformIdentifier(cu);
		jField.type = this.transformType(cu);

		return jField;
	}

	private List<JavaMethod> transformMethods(CodeUnit cu) {
		return cu.getSubCodeUnits()
				.stream()
				.filter(unit -> unit.getType() == CodeUnitType.METHOD)
				.map(this::transformMethod)
				.collect(Collectors.toList());
	}

	private JavaMethod transformMethod(CodeUnit cu) {
		JavaMethod jMethod = new JavaMethod();

		jMethod.modifiers = this.transformModifier(cu);
		jMethod.identifier = this.transformIdentifier(cu);
		jMethod.returnType = this.transformReturnType(cu);
		jMethod.parameters = this.transformParameters(cu);
		jMethod.body = this.transformBody(cu);

		return jMethod;
	}

	private String transformType(CodeUnit cu) {
		Class c = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
		return c.getName();
	}

	private String transformReturnType(CodeUnit cu) {
		Class c = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.RETURN_TYPE).getDatumData();
		return c.getName();
	}

	private List<JavaMethodParameter> transformParameters(CodeUnit cu) {
		return cu.getSubCodeUnits()
				.stream()
				.filter(unit -> unit.getType() == CodeUnitType.METHOD_PARAM)
				.map(this::transformParameter)
				.collect(Collectors.toList());
	}

	private JavaMethodParameter transformParameter(CodeUnit cu) {
		JavaMethodParameter jParam = new JavaMethodParameter();

		jParam.identifier = (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();

		Class returnType = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
		jParam.type = returnType.getName();

		return jParam;
	}

	//todo METHOD BODY for now is the only 1:1 cardinality of R(CodeUnit, CodeUnit), should this stay?
	private JavaMethodBody transformBody(CodeUnit cu) {
		JavaMethodBody jBody = new JavaMethodBody();

		Optional<CodeUnit> optionalCodeUnit = cu.getSubCodeUnits()
				.stream()
				.filter(unit -> unit.getType() == CodeUnitType.METHOD_BODY)
				.findFirst();

		if(optionalCodeUnit.isPresent()) {
			CodeUnit bodyCodeUnit = optionalCodeUnit.get();
			String codeBodyString = (String) bodyCodeUnit
					.getCodeUnitDatum(CodeUnitDatumType.METHOD_BODY_STRING)
					.getDatumData();
			jBody.setContentFromString(codeBodyString);
		}

		return jBody;
	}
}
