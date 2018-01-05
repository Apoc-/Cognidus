/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 21:14
 */
package sphynx.ubgenerator;

import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitModifier;
import sphynx.unitmodel.CodeUnitType;

import java.lang.reflect.Type;

public class UBCodeUnitBuilder {
	private CodeUnit codeUnit;

	private UBCodeUnitBuilder() {
		this.codeUnit = new CodeUnit(CodeUnitType.CLASS);
	}

	public static UBCodeUnitBuilder createWithIdentifier(String identifier) {
		UBCodeUnitBuilder cb = new UBCodeUnitBuilder();
		cb.codeUnit.addCodeUnitDatum("identifier", identifier);
		return cb;
	}

	public UBCodeUnitBuilder withModifiers(CodeUnitModifier... modifiers) {
		this.codeUnit.addCodeUnitDatum("modifier", modifiers);
		return this;
	}

	public UBCodeUnitBuilder withDataType(Type dataType) {
		this.codeUnit.addCodeUnitDatum("dataType",dataType);
		return this;
	}

	public UBCodeUnitBuilder withSubCodeUnit(CodeUnit codeUnit) {
		this.codeUnit.addSubCodeUnit(codeUnit);
		return this;
	}

	public CodeUnit end() {
		return codeUnit;
	}
}
