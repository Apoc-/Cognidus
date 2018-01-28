/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:39
 */

import cherry.generated.POJOUnitBuilder;
import cherry.generated.PublicIntUnitBuilder;
import cherry.generated.VarUnitBuilder;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitModifier;
import jade.CodeUnitTransformator;
import scarlet.generator.JavaClassGenerator;
import scarlet.model.JavaClassFile;

import java.io.IOException;

class TransformatorTester {
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
		jcg.generateJavaFileFromModel(j);
	}
}