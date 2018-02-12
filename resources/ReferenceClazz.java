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
public class ReferenceClazz {
	@FixedCodeUnit @HasGetter @HasSetter
	private int id;

	@FixedCodeUnit
	private ReferenceClazz() { }

	@FixedCodeUnit
	public ReferenceClazz(int id) {
		this.id = id;
	}
}