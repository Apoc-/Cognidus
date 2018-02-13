/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:39
 */


import cherry.generated.ReferenceClazz.ClazzUnitBuilder;
import cherry.generated.ReferencePOJO.*;
import cherry.generated.SingletonClass.SingletonUnitBuilder;
import cherry.generated.VarSingletonClass.InstanceVarUnitBuilder;
import cherry.generated.VarSingletonClass.VarSingletonUnitBuilder;
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
						.withDataType(float.class.getName())
						.withModifiers(CodeUnitModifier.PRIVATE)
						.end())
				.withField(VarUnitBuilder
						.createWithIdentifier("Ro")
						.withDataType(String.class.getName())
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

		TransformCodeUnit(cu);
	}

	@org.junit.jupiter.api.Test
	void MethodBuilderTest() throws IOException {
		CodeUnit cu = POJOUnitBuilder
				.createWithIdentifier("Clazz")
				.withMethod(MethodUnitBuilder
						.createWithIdentifier("Method")
						.withMethodBody("//test;")
						.withReturnType(void.class.getName())
						.end())
				.withMethod(MethodModUnitBuilder
						.createWithIdentifier("MethodM")
						.withModifiers(CodeUnitModifier.PRIVATE)
						.withMethodBody("//test2;")
						.withReturnType(void.class.getName())
						.end())
				.withMethod(MethodModParamUnitBuilder
						.createWithIdentifier("MethodMP")
						.withModifiers(CodeUnitModifier.PRIVATE, CodeUnitModifier.STATIC)
						.withParameter("a", int.class.getName())
						.withParameter("b", int.class.getName())
						.withMethodBody("return a + b;")
						.withReturnType(int.class.getName())
						.end())
				.end();

		System.out.println(cu);

		TransformCodeUnit(cu);
	}

	@org.junit.jupiter.api.Test
	void ConstructorBuilderTest() throws IOException {
		CodeUnit cu = ClazzUnitBuilder
				.createWithIdentifier("SimpleClazz")
				.withModifiers(CodeUnitModifier.PUBLIC)
				.end();

		System.out.println(cu);

		TransformCodeUnit(cu);
	}

	@org.junit.jupiter.api.Test
	void SingletonBuilderTest() throws IOException {
		CodeUnit cu = SingletonUnitBuilder
				.createWithIdentifier("DataManager")
				.end();

		System.out.println(cu);

		TransformCodeUnit(cu);
	}

	@org.junit.jupiter.api.Test
	void VarSingletonBuilderTest() throws IOException {
		CodeUnit cu = VarSingletonUnitBuilder
				.createWithIdentifier("DataManager")
				.withField(InstanceVarUnitBuilder
						.createWithIdentifier("varInstance")
						.end())
				.end();

		System.out.println(cu);

		TransformCodeUnit(cu);
	}

	private void TransformCodeUnit(CodeUnit cu) throws IOException {
		CodeUnitTransformator cut = new CodeUnitTransformator();
		JavaClassFile j = new JavaClassFile();
		j.model = cut.transformClassCodeUnit(cu);

		JavaClassGenerator jcg = new JavaClassGenerator();
		jcg.generateJavaFileFromModel(j);
	}
}