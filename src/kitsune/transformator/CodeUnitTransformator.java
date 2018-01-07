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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Todo replace this quick and dirty approach
public class CodeUnitTransformator {
	public JavaClass transformClassCodeUnit(CodeUnit cu) {
		JavaClass clazz = new JavaClass();


		clazz.name = this.parseIdentifier(cu);
		clazz.modifiers = this.parseModifier(cu);
		clazz.fields = this.parseFields(cu);

		return clazz;
	}

	private String parseIdentifier(CodeUnit cu) {
		return (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();
	}

	private Set<JavaModifier> parseModifier(CodeUnit cu) {
		CodeUnitModifier[] cm = (CodeUnitModifier[]) cu.getCodeUnitDatum(CodeUnitDatumType.MODIFIER).getDatumData();
		return Arrays
				.stream(cm)
				.map(m -> JavaModifier.valueOf(m.name()))
				.collect(Collectors.toSet());
	}

	private List<JavaField> parseFields(CodeUnit cu) {
		 return cu.getSubCodeUnits()
				.stream()
				.filter(unit -> unit.getType() == CodeUnitType.FIELD)
				.map(fieldUnit -> parseField(fieldUnit))
				.collect(Collectors.toList());
	}

	private JavaField parseField(CodeUnit cu) {
		JavaField jField = new JavaField();
		jField.modifiers = this.parseModifier(cu);
		jField.name = this.parseIdentifier(cu);
		jField.type = this.parseType(cu);

		return jField;
	}

	private String parseType(CodeUnit cu) {
		Class c = (Class) cu.getCodeUnitDatum(CodeUnitDatumType.DATA_TYPE).getDatumData();
		return c.getName();
	}
}
