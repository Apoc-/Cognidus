/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.unitmodel;

import java.util.LinkedList;
import java.util.List;

public class CodeUnitDatum<T> {
	private String datumName; //as in "identifier", "modifier", "dataType" //TODO Needs better name
	private List<T> datumData;

	CodeUnitDatum(String datumName, List<T> datumData) {
		this.datumName = datumName;
		this.datumData = new LinkedList<>();
		this.datumData.addAll(datumData);
	}

	CodeUnitDatum(String datumName, T datumData) {
		this.datumName = datumName;
		this.datumData = new LinkedList<>();
		this.datumData.add(datumData);
	}

	String getDatumName() {
		return datumName;
	}
}
