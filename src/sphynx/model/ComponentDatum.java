/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.model;

import java.util.*;

public class ComponentDatum {
	public String typeName;
	public String[] data;

	public ComponentDatum(String typeName, String... data) {
		this.typeName = typeName;
		this.data = data;
	}
}
