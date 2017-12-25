/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import sphynx.annotations.Component;
import sphynx.annotations.Renamable;

@Component("POJO")
@Renamable
public abstract class ReferencePOJO {
	public float val;
	protected String name;
	int id;
	private String[] secrets;
}