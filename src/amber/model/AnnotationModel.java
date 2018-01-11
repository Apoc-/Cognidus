/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:45
 */

package amber.model;

import cherry.model.CodeUnit;

import java.util.EnumSet;

public class AnnotationModel {
	private String identifier;
	private EnumSet<AnnotationDatum> annotationData;
	private CodeUnit defaultCodeUnit;

	public AnnotationModel() {
		annotationData = EnumSet.noneOf(AnnotationDatum.class);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public EnumSet<AnnotationDatum> getAnnotationData() {
		return annotationData;
	}

	public void addAnnotationDatum(AnnotationDatum annotationDatum) {
		if(annotationData == null) {
			annotationData = EnumSet.of(annotationDatum);
		} else {
			this.annotationData.add(annotationDatum);
		}
	}

	public CodeUnit getDefaultCodeUnit() {
		return defaultCodeUnit;
	}

	public void setDefaultCodeUnit(CodeUnit codeUnit) {
		this.defaultCodeUnit = codeUnit;
	}
}
