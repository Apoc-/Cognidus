/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:09
 */
package cherry.model;

public class CodeUnitBuilder {
	private final CodeUnit codeUnit;

	private CodeUnitBuilder() {
		this.codeUnit = new CodeUnit(CodeUnitType.CLASS);
	}

	public static CodeUnitBuilder createWithIdentifier(String identifier) {
		CodeUnitBuilder cb = new CodeUnitBuilder();
		cb.codeUnit.addCodeUnitDatum(CodeUnitDatumType.IDENTIFIER, identifier);
		return cb;
	}

	public static CodeUnitBuilder create() {
		return new CodeUnitBuilder();
	}

	public CodeUnitBuilder setCodeUnitType(CodeUnitType type) {
		this.codeUnit.setType(type);
		return this;
	}

	public CodeUnitBuilder withModifiers(CodeUnitModifier... modifiers) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.MODIFIER, modifiers);
		return this;
	}

	public CodeUnitBuilder withDataType(String typeName) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, typeName);
		return this;
	}

	public CodeUnitBuilder withReturnType(String typeName) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.RETURN_TYPE, typeName);
		return this;
	}

	public CodeUnitBuilder withMethodBody(String body) {
		this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.METHOD_BODY_STRING, body);
		return this;
	}

	public CodeUnit end() {
		return codeUnit;
	}
}
