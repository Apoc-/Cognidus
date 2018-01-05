/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.unitmodel;

import java.util.*;

public class CodeUnit {
	private CodeUnitType type;
	private Map<String, CodeUnitDatum> data;
	private List<CodeUnit> subCodeUnits;

	public CodeUnit(CodeUnitType type) {
		this.type = type;
		this.data = new HashMap<>();
		this.subCodeUnits = new ArrayList<>();
	}

	public <T> void addCodeUnitDatum(String datumName, List<T> datumData) {
		CodeUnitDatum datum = new CodeUnitDatum<>(datumName , Arrays.asList(datumData));
		data.put(datum.getDatumName(),datum);
	}

	public <T> void addCodeUnitDatum(String datumName, T datumData) {
		CodeUnitDatum datum = new CodeUnitDatum<>(datumName , Arrays.asList(datumData));
		data.put(datum.getDatumName(),datum);
	}

	public void addSubCodeUnit(CodeUnit subCodeUnit) {
		subCodeUnits.add(subCodeUnit);
	}
}
