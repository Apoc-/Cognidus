/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:34
 */
package wolpertinger.javamodel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JavaClass {
	public String className;
	public Set<JavaModifier> classModifiers;
	public List<JavaField> javaFields;

	public JavaClass() {
		this.className = "";
		this.classModifiers = new HashSet<>();
		this.javaFields = new LinkedList<>();
	}
}
