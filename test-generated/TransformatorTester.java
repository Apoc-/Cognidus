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
				.withMethod(MethodUnitBuilder
						.createWithIdentifier("Method")
						.withMethodBody("//test;")
						.withReturnType(void.class)
						.end())
				.withMethod(MethodModUnitBuilder
						.createWithIdentifier("MethodM")
						.withModifiers(CodeUnitModifier.PRIVATE)
						.withMethodBody("//test2;")
						.withReturnType(void.class)
						.end())
				.withMethod(MethodModParamUnitBuilder
						.createWithIdentifier("MethodMP")
						.withModifiers(CodeUnitModifier.PRIVATE, CodeUnitModifier.STATIC)
						.withParameter("a", int.class)
						.withParameter("b", int.class)
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