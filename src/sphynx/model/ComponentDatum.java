/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package sphynx.model;

import java.util.ArrayList;
import java.util.List;

public class ComponentDatum {
	public String type;
	public String[] values;
	public boolean changeable;

	public ComponentDatum(String type, String... values) {
		this.type = type;
		this.values = values;
		this.changeable = false;
	}
}
