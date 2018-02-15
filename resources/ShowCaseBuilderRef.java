/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 15.02.18 20:20
 */

import amber.annotations.CodeUnit;
import amber.annotations.FixedCodeUnit;

@CodeUnit("Builder")
public class ShowCaseBuilderRef {
	private final String data;

	private ShowCaseBuilderRef() {
		this.data = new String();
	}

	@FixedCodeUnit
	public static ShowCaseBuilderRef create() {
		return new ShowCaseBuilderRef();
	}

	@FixedCodeUnit
	public String end() {
		return data;
	}
}
