/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.unitmodel;

import java.io.Serializable;
import java.util.*;

public class CodeUnit implements Serializable {
	private CodeUnitType type;
	private Map<String, CodeUnitDatum> data;
	private List<CodeUnit> subCodeUnits;

	public CodeUnit(CodeUnitType type) {
		this.type = type;
		this.data = new HashMap<>();
		this.subCodeUnits = new ArrayList<>();
	}

	//not used for now
	public CodeUnit(CodeUnit sourceCodeUnit) {
		this.setType(sourceCodeUnit.getType());
		this.data = new HashMap<>(sourceCodeUnit.getData());
		this.subCodeUnits = new ArrayList<>();
		sourceCodeUnit
				.getSubCodeUnits()
				.forEach(cu -> this.addSubCodeUnit(new CodeUnit(cu)));
	}

	public <T> void addCodeUnitDatum(String datumName, List<T> datumData) {
		CodeUnitDatum datum = new CodeUnitDatum<>(datumName , datumData);
		data.put(datum.getDatumName(),datum);
	}

	public <T> void addCodeUnitDatum(String datumName, T datumData) {
		CodeUnitDatum datum = new CodeUnitDatum<>(datumName , datumData);
		data.put(datum.getDatumName(),datum);
	}

	public Map<String, CodeUnitDatum> getData() {
		return data;
	}

	public void addSubCodeUnit(CodeUnit subCodeUnit) {
		subCodeUnits.add(subCodeUnit);
	}

	public List<CodeUnit> getSubCodeUnits() {
		return subCodeUnits;
	}

	public CodeUnitType getType() {
		return type;
	}

	public void setType(CodeUnitType type) {
		this.type = type;
	}
}
