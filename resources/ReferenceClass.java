/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 09.02.18 19:35
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableType;

@CodeUnit("Clazz") @VariableModifier
public class ReferenceClass {
	@FixedCodeUnit @HasGetter @HasSetter
	private int id;

	@FixedCodeUnit
	private ReferenceClass() { }

	@FixedCodeUnit
	public ReferenceClass(int id) {
		this.id = id;
	}
}