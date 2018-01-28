/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:21
 */
package scarlet.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JavaMethod {
	public Set<JavaModifier> modifiers;
	public String returnType;
	public String identifier;
	public List<JavaMethodParameter> parameters;
	public JavaMethodBody body;

	public JavaMethod() {
		modifiers = new HashSet<>();
		identifier = "";
		returnType = "";
		parameters = new LinkedList<>();
		body = new JavaMethodBody();
	}
}
