/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 17.12.17 20:15
 */

import sphynx.annotations.Component;
import sphynx.annotations.Renamable;
import sphynx.annotations.VariableModifier;
import sphynx.annotations.VariableType;

@CodeUnit("POJO")
@VariableModifier()
public abstract class ReferencePOJO {
	int id;
	@Unit("StringField") @Renamable String name;
}