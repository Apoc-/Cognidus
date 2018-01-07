/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.unitmodel;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class CodeUnit implements Serializable {
	private CodeUnitType type;
	private final Map<CodeUnitDatumType, CodeUnitDatum> data;
	private final List<CodeUnit> subCodeUnits;

	public CodeUnit(CodeUnitType type) {
		this.type = type;
		this.data = new HashMap<>();
		this.subCodeUnits = new ArrayList<>();
	}

	public <T> void addCodeUnitDatum(CodeUnitDatumType datumType, T datumData) {
		CodeUnitDatum<T> datum = new CodeUnitDatum<>(datumData);
		data.put(datumType,datum);
	}

	public Map<CodeUnitDatumType, CodeUnitDatum> getData() {
		return data;
	}

	public CodeUnitDatum getCodeUnitDatum(CodeUnitDatumType type) {
		return data.get(type);
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

	@Override
	public String toString() {
		String dataString = data
				.entrySet()
				.stream()
				.map(entry -> entry.getKey().toString() + " = " + entry.getValue().toString() + "")
				.collect(Collectors.joining(", "));

		return type.toString() + ": " + dataString + " with " + subCodeUnits.size() + " SubCodeUnits";
	}
}
