/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 07.01.18 16:14
 */

package sphynx.unitmodel;

public enum CodeUnitDatumType {
	MODIFIER,
	IDENTIFIER,
	DATA_TYPE;

	@Override
	public String toString() {
		return this.name();
	}
}
