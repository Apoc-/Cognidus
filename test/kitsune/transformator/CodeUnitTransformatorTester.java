/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 07.01.18 21:42
 */

package kitsune.transformator;

import sphynx.generated.POJOUnitBuilder;
import sphynx.generated.PublicIntUnitBuilder;
import sphynx.generated.VarUnitBuilder;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitModifier;
import wolpertinger.generator.JavaClassGenerator;
import wolpertinger.javamodel.JavaClassFile;

import java.io.IOException;

class CodeUnitTransformatorTester {
	@org.junit.jupiter.api.Test
	void test() throws IOException {
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

		CodeUnitTransformator cut = new CodeUnitTransformator();
		JavaClassFile j = new JavaClassFile();
		j.model = cut.transformClassCodeUnit(cu);

		JavaClassGenerator jcg = new JavaClassGenerator();
		jcg.GenerateJavaFileFromModel(j);
	}
}