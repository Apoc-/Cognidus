/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 13.02.18 12:16
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableParams;

import java.util.List;

@CodeUnit("VarSingleton")
public class VarSingletonClass {
	@FixedCodeUnit
	private static VarSingletonClass instance;

	@FixedCodeUnit
	private List<String> things;

	@CodeUnit("InstanceVar")
	private static VarSingletonClass varInstance;

	@CodeUnit("Constructor") @VariableModifier @VariableParams
	public VarSingletonClass(int a) {

	}

	@FixedCodeUnit
	private VarSingletonClass() { }

	@FixedCodeUnit
	public VarSingletonClass(VarSingletonClass source) {
		VarSingletonClass vsc = new VarSingletonClass();
	}

	@FixedCodeUnit
	public static VarSingletonClass getInstance() {
		if(instance == null)
			instance = new VarSingletonClass();

		return instance;
	}

	@FixedCodeUnit
	public static VarSingletonClass identity(VarSingletonClass singletonClass) {
		return singletonClass;
	}
}