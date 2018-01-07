/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 21:14
 */
package sphynx.generator;

import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitDatumType;
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
		cb.codeUnit.addCodeUnitDatum(CodeUnitDatumType.IDENTIFIER, identifier);
		return cb;
	}

	public UBCodeUnitBuilder setCodeUnityType(CodeUnitType type) {
		this.codeUnit.setType(type);
		return this;
	}

	public UBCodeUnitBuilder withModifiers(CodeUnitModifier... modifiers) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.MODIFIER, modifiers);
		return this;
	}

	public UBCodeUnitBuilder withDataType(Class dataType) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE,dataType);
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
