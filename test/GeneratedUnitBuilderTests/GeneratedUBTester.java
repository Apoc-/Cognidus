/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 07.01.18 12:25
 */
package GeneratedUnitBuilderTests;

import sphynx.generated.POJOUnitBuilder;
import sphynx.generated.PublicIntUnitBuilder;
import sphynx.generated.VarUnitBuilder;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitModifier;

import java.lang.reflect.Modifier;
import java.util.List;

public class GeneratedUBTester {
	@org.junit.jupiter.api.Test
	void testBuilders() {
		CodeUnit cu = POJOUnitBuilder
				.createWithIdentifier("Foo")
				.withSubCodeUnit(VarUnitBuilder
						.createWithIdentifier("Fus")
						.withDataType(float.class)
						.withModifiers(CodeUnitModifier.PRIVATE)
						.end())
				.withSubCodeUnit(VarUnitBuilder
						.createWithIdentifier("Ro")
						.withDataType(String.class)
						.withModifiers(CodeUnitModifier.PUBLIC, CodeUnitModifier.TRANSIENT)
						.end())
				.withSubCodeUnit(PublicIntUnitBuilder
						.createWithIdentifier("Dah")
						.end())
				.end();

		System.out.println(cu);
	}
}
