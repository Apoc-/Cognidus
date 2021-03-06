/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 19:34
 */

package cherry.model;

public enum CodeUnitModifier {
	ABSTRACT,
	FINAL,
	PUBLIC,
	PROTECTED,
	PRIVATE,
	STATIC,
	TRANSIENT;

	@Override
	public String toString() {
		return this.name();
	}
}
