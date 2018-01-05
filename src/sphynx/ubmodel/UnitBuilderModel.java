/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 18:20
 */

package sphynx.ubmodel;

import sphynx.ubgenerator.UBMethodSpec;
import sphynx.unitmodel.CodeUnit;

import java.util.EnumSet;

public class UnitBuilderModel {
	private String identifier;
	private EnumSet<UBMethodSpec> builderMethods;
	private CodeUnit defaultCodeUnit;

	public UnitBuilderModel() {
		this.identifier = null;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public EnumSet<UBMethodSpec> getBuilderMethods() {
		return builderMethods;
	}

	public void addBuilderMethod(UBMethodSpec builderMethod) {
		if(builderMethods == null) {
			builderMethods = EnumSet.of(builderMethod);
		} else {
			this.builderMethods.add(builderMethod);
		}
	}

	public CodeUnit getDefaultCodeUnit() {
		return defaultCodeUnit;
	}

	public void setDefaultCodeUnit(CodeUnit defaultCodeUnit) {
		this.defaultCodeUnit = defaultCodeUnit;
	}
}
