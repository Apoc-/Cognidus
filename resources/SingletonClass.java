/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 12.02.18 14:00
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
	private SingletonClass() { }

	@FixedCodeUnit
	public static SingletonClass getInstance() {
		if(instance == null)
			instance = new SingletonClass();

		return instance;
	}

	public static SingletonClass identity(SingletonClass singletonClass) {
		return singletonClass;
	}
}