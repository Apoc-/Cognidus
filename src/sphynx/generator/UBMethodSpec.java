/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 18:33
 */

package sphynx.generator;

public enum UBMethodSpec {
	WITH_MODIFIERS("withModifiers"),
	WITH_DATA_TYPE("withDataType"),
	WITH_SUB_CODEUNIT("withSubCodeUnit"),
	CREATE_WITH_IDENTIFIER("createWithIdentifier"),
	END("end");

	private String methodName;

	UBMethodSpec(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return this.methodName;
	}
}