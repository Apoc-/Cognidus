/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 04.01.18 19:47
 */

package cherry.model;

public enum CodeUnitType {
	CLASS,
	FIELD,
	METHOD,
	METHOD_BODY, //todo possibly make this a codeunitdatum and not a sub codeunit
	METHOD_PARAM;

	@Override
	public String toString() {
		return this.name();
	}
}