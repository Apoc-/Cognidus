/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:39
 */


import cherry.generated.ExampleRef.ConstructorUnitBuilder;

import cherry.generated.ExampleRef.ExampleClassUnitBuilder;
import cherry.generated.ReferenceClass.*;
import cherry.generated.SingletonClass.SingletonUnitBuilder;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitModifier;
import com.squareup.javapoet.JavaFile;
import jade.transformator.CodeUnitTransformator;
import scarlet.generator.JavaClassGenerator;
import scarlet.model.JavaClassFile;
import violet.Logger;

import java.io.File;
import java.io.IOException;

class TransformatorTester {
	@org.junit.jupiter.api.Test
	void FixedConstructorBuilderTest() throws IOException {
		CodeUnit cu = ClazzUnitBuilder
				.createWithIdentifier("SimpleClazz")
				.withModifiers(CodeUnitModifier.PUBLIC)
				.end();

		System.out.println(cu);

		TransformCodeUnit(cu);
	}

	@org.junit.jupiter.api.Test
	void ConstructorBuilderTest() throws IOException {
		CodeUnit cu = ClazzUnitBuilder
				.createWithIdentifier("ConstructorTestClass")
				.withConstructor(ConstructorUnitBuilder
						.create()
						.withMethodBody("//test")
						.withModifiers(CodeUnitModifier.PUBLIC)
						.withParameter("arg", String.class.getName())
						.end())
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
	void ExampleTester() throws IOException {
		CodeUnit cu = ExampleClassUnitBuilder
				.createWithIdentifier("ExampleIdentifier")
				.withModifiers(CodeUnitModifier.PROTECTED)
				.withField(GetVarUnitBuilder
						.createWithIdentifier("exampleField")
						//.withModifiers(CodeUnitModifier.PRIVATE)
						.withDataType(Object.class.getName())
						.end())
				.withMethod(MethodUnitBuilder
					.createWithIdentifier("exampleMethod")
						.withModifiers(CodeUnitModifier.PUBLIC)
						.withParameter("param", String.class.getName())
						.withMethodBody("return param")
						.withReturnType(String.class.getName())
					.end())
				.withConstructor(ConstructorUnitBuilder
					.create()
						.withParameter("param", String.class.getName())
						.withModifiers(CodeUnitModifier.PUBLIC)
						.withMethodBody("//body als String")
						.end())
				.end();

		Logger.console().logInfo("Generated: " + cu.toString() + "\n");

		TransformCodeUnit(cu);
	}



	@org.junit.jupiter.api.Test
	void ShowCaseTester() throws IOException {
		CodeUnit dataLogger = BuildDataLogger();
		CodeUnit dataLoggerTester = BuildDataLoggerTester();

		System.out.println(dataLogger);
		System.out.println(dataLoggerTester);

		WriteToFile(dataLogger);
		WriteToFile(dataLoggerTester);
	}

	private void WriteToFile(CodeUnit cu) throws IOException {
		JavaFile jf = TransformToJavaFile(cu, "scarlet.generated");
		jf.writeTo(new File("src-generated/"));
	}

	private CodeUnit BuildDataLogger() {
		return SingletonUnitBuilder
				.createWithIdentifier("DataLogger")
				.withField(GetVarUnitBuilder
						.createWithIdentifier("logCount")
						.withDataType(int.class.getName())
						.end())
				.withMethod(PublicMethodUnitBuilder
						.createWithIdentifier("logInfo")
						.withParameter("message", String.class.getName())
						.withMethodBody("this.log(\"[info]\", message);")
						.end())
				.withMethod(PublicMethodUnitBuilder
						.createWithIdentifier("logError")
						.withParameter("message", String.class.getName())
						.withMethodBody("this.log(\"[error]\", message);")
						.end())
				.withMethod(PrivateMethodUnitBuilder
						.createWithIdentifier("log")
						.withParameter("prefix", String.class.getName())
						.withParameter("message", String.class.getName())
						.withMethodBody("System.out.println(prefix + \" \" + message);\n" +
										"logCount++;")
						.end())
				.end();
	}

	private CodeUnit BuildDataLoggerTester() {
		return ClazzUnitBuilder
				.createWithIdentifier("DataLoggerTester")
				.withMethod(PublicStaticMethodUnitBuilder
						.createWithIdentifier("main")
						.withMethodBody("DataLogger logger = DataLogger.getInstance();\n" +
								"logger.logInfo(\"Hello, World!\");\n" +
								"logger.logError(\"Don't Panic!\");\n" +
								"int c = logger.getLogCount();\n" +
								"logger.logInfo(\"Logged Messages: \" + c);")
						.end())
				.end();
	}

	private void TransformCodeUnit(CodeUnit cu) throws IOException {
		CodeUnitTransformator cut = new CodeUnitTransformator();
		JavaClassFile j = new JavaClassFile();
		j.javaClass = cut.transformClassCodeUnit(cu);

		JavaClassGenerator jcg = new JavaClassGenerator();
		JavaFile javaFile = jcg.generateJavaFileFromModel(j);
		javaFile.writeTo(System.out);
	}

	private JavaFile TransformToJavaFile(CodeUnit cu, String packageName) throws IOException {
		CodeUnitTransformator cut = new CodeUnitTransformator();
		JavaClassFile j = new JavaClassFile();
		j.javaClass = cut.transformClassCodeUnit(cu);
		j.packageName = packageName;

		JavaClassGenerator jcg = new JavaClassGenerator();
		return jcg.generateJavaFileFromModel(j);
	}
}