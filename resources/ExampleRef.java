/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 28.02.18 00:26
 */

import amber.annotations.*;

@CodeUnit("ExampleClass") @VariableModifier
public class ExampleRef {
	@FixedCodeUnit @HasGetter @HasSetter
	private int id;

	@FixedCodeUnit
	protected static float f;

	@CodeUnit("PublicString") @HasGetter @HasSetter
	public String s;

	@CodeUnit("Variable") @VariableModifier @VariableType
	Object o;

	@FixedCodeUnit
	private ExampleRef() {
		//...
	}

	@CodeUnit("Constructor") @VariableModifier @VariableParams
	public ExampleRef(int i) {
		//...
	}

	@FixedCodeUnit
	public static void ExampleStaticMethod() {
		//
	}

	@CodeUnit("Method") @VariableModifier @VariableParams
	void ExampleMethod() {
		//..
	}
}
