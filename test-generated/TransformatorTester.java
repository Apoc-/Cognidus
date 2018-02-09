/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:39
 */

import cherry.generated.*;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitModifier;
import jade.CodeUnitTransformator;
import scarlet.generator.JavaClassGenerator;
import scarlet.model.JavaClassFile;

import java.io.IOException;

class TransformatorTester {
	@org.junit.jupiter.api.Test
	void ClassAndFieldBuilderTest() throws IOException {
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
				.withSubCodeUnit(PublicIntUnitBuilder
						.createWithIdentifier("DahDah")
						.end())
				.end();

		System.out.println(cu);

		CodeUnitTransformator cut = new CodeUnitTransformator();
		JavaClassFile j = new JavaClassFile();
		j.model = cut.transformClassCodeUnit(cu);

		JavaClassGenerator jcg = new JavaClassGenerator();
		jcg.generateJavaFileFromModel(j);
	}

	@org.junit.jupiter.api.Test
	void MethodBuilderTest() throws IOException {
		CodeUnit cu = POJOUnitBuilder
				.createWithIdentifier("Clazz")
				.withSubCodeUnit(MethodUnitBuilder
						.createWithIdentifier("Method")
						.withMethodBody("//test;")
						.withReturnType(void.class)
						.end())
				.withSubCodeUnit(MethodModUnitBuilder
						.createWithIdentifier("MethodM")
						.withModifiers(CodeUnitModifier.PRIVATE)
						.withMethodBody("//test2;")
						.withReturnType(void.class)
						.end())
				.withSubCodeUnit(MethodModParamUnitBuilder
						.createWithIdentifier("MethodMP")
						.withModifiers(CodeUnitModifier.PRIVATE, CodeUnitModifier.STATIC)
						.addParameter("a", int.class)
						.addParameter("b", int.class)
						.withMethodBody("return a + b;")
						.withReturnType(int.class)
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