/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 18:33
 */

package cherry.generator;

public enum BuilderMethodType {
	WITH_MODIFIERS("withModifiers"),
	WITH_DATA_TYPE("withDataType"),
	WITH_SUB_CODEUNIT("withSubCodeUnit"),
	WITH_FIELD("withField"),
	WITH_METHOD("withMethod"),
	WITH_CONSTRUCTOR("withConstructor"),
	CREATE_WITH_IDENTIFIER("createWithIdentifier"),
	CREATE("create"),
	CONSTRUCTOR(""),
	CLASSBUILDER_END("end"),
	END("end"),
	INIT_DEF_CODE_UNIT("initDefCodeUnit"),
	WITH_PARAMETER("withParameter"),
	WITH_METHOD_BODY("withMethodBody"),
	WITH_RETURN_TYPE("withReturnType");

	private final String methodName;

	BuilderMethodType(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return this.methodName;
	}
}
