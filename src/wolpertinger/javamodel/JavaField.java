/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:27
 */
package wolpertinger.javamodel;

import java.util.HashSet;
import java.util.Set;

public class JavaField {
	public final Set<JavaModifier> modifiers;
	public String type;
	public String name;
	public Boolean isArray;

	public JavaField() {
		modifiers = new HashSet<>();
		name = "";
		isArray = false;
	}
}
