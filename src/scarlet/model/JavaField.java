/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:27
 */
package scarlet.model;

import java.util.HashSet;
import java.util.Set;

public class JavaField {
	public Set<JavaModifier> modifiers;
	public String type;
	public String identifier;
	public Boolean isArray;

	public JavaField() {
		modifiers = new HashSet<>();
		identifier = "";
		isArray = false;
	}
}
