/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 04.01.18 19:47
 */

package sphynx.unitmodel;

public enum CodeUnitType {
	CLASS,
	FIELD;

	@Override
	public String toString() {
		return this.name();
	}
}