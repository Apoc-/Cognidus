/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableType;

@CodeUnit("POJO")
public abstract class ReferencePOJO {
	@FixedCodeUnit @HasGetter @HasSetter
	private transient int id;

	@FixedCodeUnit @HasSetter
	private String name;

	@CodeUnit("PublicInt") @HasGetter @HasSetter
	public int number;

	@CodeUnit("String") @VariableModifier
	public String s;

	@CodeUnit("Var") @VariableModifier @VariableType
	public float f;

	@FixedCodeUnit
	public int sum(int a, int b) {
		return a + b;
	}

	@CodeUnit("MethodModParam") @VariableModifier @VariableParams
	public int foo(int a) {
		return a * b;
	}

	@CodeUnit("MethodMod") @VariableModifier
	public int bar(int n) {
		return a * b;
	}

	@CodeUnit("Method")
	public int bus(int m) {
		return a * b;
	}
}