/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import amber.annotations.CodeUnit;
import amber.annotations.Component;
import amber.annotations.FixedCodeUnit;
import amber.annotations.Renamable;
import amber.annotations.VariableModifier;
import amber.annotations.VariableType;

@CodeUnit("POJO")
public abstract class ReferencePOJO {
	@FixedCodeUnit
	transient private int id;

	@CodeUnit("PublicInt")
	public int number;

	@CodeUnit("String") @VariableModifier
	public String s;

	@CodeUnit("Var") @VariableModifier @VariableType
	public float f;
}