/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component {
	public String identifier;
	public String type;
	public Map<String, ComponentDatum> componentData;
	public List<Component> subComponents;

	public Component() {
		componentData = new HashMap<>();
		subComponents = new ArrayList<>();
	}
}
