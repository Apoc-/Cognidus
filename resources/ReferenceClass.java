/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 09.02.18 19:35
 */

import amber.annotations.*;

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

	@CodeUnit("GetSetVar") @HasGetter @HasSetter @VariableType
	private int getsetvar;

	@CodeUnit("GetVar") @HasGetter @VariableType
	private int getvar;

	@CodeUnit("PrivateVar") @VariableType
	private int i;

	@CodeUnit("SetVar") @HasSetter @VariableType
	private int setvar;

	@CodeUnit("Method") @VariableParams @VariableModifier
	public void Method() {}

	@CodeUnit("PrivateMethod") @VariableParams
	private void PrivateMethod() {}

	@CodeUnit("PublicMethod") @VariableParams
	public void PublicMethod() {}

	@CodeUnit("PublicStaticMethod") @VariableParams
	public static void PublicStaticMethod() {}
}