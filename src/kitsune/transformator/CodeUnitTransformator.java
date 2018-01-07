/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 07.01.18 19:24
 */

package kitsune.transformator;

import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitDatumType;
import sphynx.unitmodel.CodeUnitModifier;
import sphynx.unitmodel.CodeUnitType;
import wolpertinger.javamodel.JavaClass;
import wolpertinger.javamodel.JavaField;
import wolpertinger.javamodel.JavaModifier;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Todo replace this quick and dirty approach
public class CodeUnitTransformator {
	public JavaClass transformClassCodeUnit(CodeUnit cu) {
		JavaClass clazz = new JavaClass();

		clazz.name = this.transformIdentifier(cu);
		clazz.modifiers = this.transformModifier(cu);
		clazz.fields = this.transformFields(cu);

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
				.map(fieldUnit -> transformField(fieldUnit))
				.collect(Collectors.toList());
	}

	private JavaField transformField(CodeUnit cu) {
		JavaField jField = new JavaField();
		jField.modifiers = this.transformModifier(cu);
		jField.name = this.transformIdentifier(cu);
		jField.type = this.transformType(cu);

		return jField;
	}

	private String transformType(CodeUnit cu) {
		Class c = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
		return c.getName();
	}
}
