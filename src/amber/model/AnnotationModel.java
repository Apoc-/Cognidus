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
	private EnumSet<AnnotationType> variablityAnnotations;
	private EnumSet<AnnotationType> extensionAnnotations;
	private CodeUnit defaultCodeUnit;

	public AnnotationModel() {
		variablityAnnotations = EnumSet.noneOf(AnnotationType.class);
		extensionAnnotations = EnumSet.noneOf(AnnotationType.class);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void addVariabilityAnnotation(AnnotationType annotationType) {
		this.variablityAnnotations.add(annotationType);
	}

	public EnumSet<AnnotationType> getVariablityAnnotations() {
		return this.variablityAnnotations;
	}

	public void addExtensionAnnotation(AnnotationType annotationType) {
		this.extensionAnnotations.add(annotationType);
	}

	public EnumSet<AnnotationType> getExtensionAnnotations() {
		return this.extensionAnnotations;
	}

	public CodeUnit getDefaultCodeUnit() {
		return this.defaultCodeUnit;
	}

	public void setDefaultCodeUnit(CodeUnit codeUnit) {
		this.defaultCodeUnit = codeUnit;
	}
}
