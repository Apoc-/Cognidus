/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:24
 */

import cherry.generated.POJOUnitBuilder;
import cherry.generated.PublicIntUnitBuilder;
import cherry.generated.VarUnitBuilder;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitModifier;

class GeneratedBuilderTester {
	@org.junit.jupiter.api.Test
	void testBuilders() {
		CodeUnit cu = POJOUnitBuilder
				.createWithIdentifier("Foo")
				.withField(VarUnitBuilder
						.createWithIdentifier("Fus")
						.withDataType(float.class)
						.withModifiers(CodeUnitModifier.PRIVATE)
						.end())
				.withField(VarUnitBuilder
						.createWithIdentifier("Ro")
						.withDataType(String.class)
						.withModifiers(CodeUnitModifier.PUBLIC, CodeUnitModifier.TRANSIENT)
						.end())
				.withField(PublicIntUnitBuilder
						.createWithIdentifier("Dah")
						.end())
				.withField(PublicIntUnitBuilder
						.createWithIdentifier("DahDah")
						.end())
				.end();

		System.out.println(cu);
	}
}