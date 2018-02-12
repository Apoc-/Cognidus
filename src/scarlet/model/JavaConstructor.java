/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 28.01.18 11:25
 */
package scarlet.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JavaConstructor {
	public Set<JavaModifier> modifiers;
	public JavaClass javaClass;
	public List<JavaMethodParameter> parameters;
	public JavaMethodBody body;

	public JavaConstructor(JavaClass javaClass) {
		this.modifiers = new HashSet<>();
		this.parameters = new LinkedList<>();
		this.body = new JavaMethodBody();
		this.javaClass = javaClass;
	}
}
