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
	public String name;
	public Set<JavaModifier> modifiers;
	public List<JavaField> fields;

	public JavaClass() {
		this.name = "";
		this.modifiers = new HashSet<>();
		this.fields = new LinkedList<>();
	}
}
