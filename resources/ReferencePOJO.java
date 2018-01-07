/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import sphynx.annotations.CodeUnit;
import sphynx.annotations.Component;
import sphynx.annotations.FixedCodeUnit;
import sphynx.annotations.Renamable;
import sphynx.annotations.VariableModifier;
import sphynx.annotations.VariableType;

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