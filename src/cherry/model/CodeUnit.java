/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package cherry.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class CodeUnit implements Serializable {
	private CodeUnit parent;
	private CodeUnitType type;
	private final Map<CodeUnitDatumType, CodeUnitDatum> data;
	private final List<CodeUnit> subCodeUnits;

	public CodeUnit(CodeUnitType type) {
		this.type = type;
		this.data = new HashMap<>();
		this.subCodeUnits = new ArrayList<>();
	}

	public boolean isType(CodeUnitType type) {
		return getType() == type;
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

	public boolean hasDatum(CodeUnitDatumType type) {
		return data.containsKey(type);
	}

	public void addSubCodeUnit(CodeUnit subCodeUnit) {
		subCodeUnit.parent = this;
		subCodeUnits.add(subCodeUnit);
	}

	public void addSubCodeUnits(List<CodeUnit> subCodeUnits) {
		subCodeUnits.forEach(this::addSubCodeUnit);
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

	public CodeUnit getParent() {
		return parent;
	}

	public void setParent(CodeUnit parent) {
		this.parent = parent;
	}

	public boolean hasParent() {
		return this.parent != null;
	}
}
