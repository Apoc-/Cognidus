/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import amber.annotations.*;

import java.util.List;

@CodeUnit("POJO")
public abstract class ReferencePOJO {
	@FixedCodeUnit @HasGetter @HasSetter
	private transient int id;

	@FixedCodeUnit @HasSetter
	private String name;

	@CodeUnit("PublicInt") @HasGetter @HasSetter
	public int number;

	@CodeUnit("StringList") @VariableModifier @HasSetter @HasGetter
	public List<String> s;

	@CodeUnit("Var") @VariableModifier @VariableType
	public float f;

	@FixedCodeUnit
	public int sum(int a, int b) {
		return a + b;
	}

	@FixedCodeUnit
	public int sum(int a, int b, int c) {
		return a + b + c;
	}

	@CodeUnit("MethodModParam") @VariableModifier @VariableParams
	public int foo(int a) {
		return a;
	}

	@CodeUnit("MethodMod") @VariableModifier
	public int bar(int n) {
		return 3*n;
	}

	@CodeUnit("Method")
	public int bus(int m) {
		return m*m;
	}
}