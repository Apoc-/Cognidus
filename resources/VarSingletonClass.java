/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 13.02.18 12:16
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableType;

@CodeUnit("VarSingleton")
public class VarSingletonClass {
	@FixedCodeUnit
	private static VarSingletonClass instance;

	@CodeUnit("InstanceVar")
	private static VarSingletonClass varInstance;

	@FixedCodeUnit
	private VarSingletonClass() { }

	@FixedCodeUnit
	public static VarSingletonClass getInstance() {
		if(instance == null)
			instance = new SingletonClass();

		return instance;
	}

	@FixedCodeUnit
	public static VarSingletonClass identity(VarSingletonClass singletonClass) {
		return singletonClass;
	}
}