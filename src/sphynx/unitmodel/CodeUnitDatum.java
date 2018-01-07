/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.unitmodel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CodeUnitDatum<T> implements Serializable {
	private List<T> datumData;

	CodeUnitDatum(List<T> datumData) {
		this.datumData = new LinkedList<>();
		this.datumData.addAll(datumData);
	}

	CodeUnitDatum(T datumData) {
		this.datumData = new LinkedList<>();
		this.datumData.add(datumData);
	}
}
