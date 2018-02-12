/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 09.02.18 19:35
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableType;

@CodeUnit("Singleton")
public class SingletonClass {
	@FixedCodeUnit
	private static SingletonClass instance;

	@FixedCodeUnit
	private ReferenceClazz() { }

	@FixedCodeUnit
	public static SingletonClass getInstance() {
		if(instance == null)
			instance = new SingletonClass();

		return instance;
	}
}