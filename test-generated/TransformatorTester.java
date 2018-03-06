/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:39
 */


import cherry.generated.ReferenceClazz.*;
import cherry.generated.ReferenceGenerics.GenericUnitBuilder;
import cherry.generated.ReferencePOJO.*;
import cherry.generated.ReferencePOJO.MethodUnitBuilder;
import cherry.generated.SingletonClass.SingletonUnitBuilder;
import cherry.generated.VarSingletonClass.ConstructorUnitBuilder;
import cherry.generated.VarSingletonClass.InstanceVarUnitBuilder;
import cherry.generated.VarSingletonClass.VarSingletonUnitBuilder;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitModifier;
import com.squareup.javapoet.JavaFile;
import jade.transformator.CodeUnitTransformator;
import scarlet.generator.JavaClassGenerator;
import scarlet.model.JavaClassFile;

import java.io.File;
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
				.withField(StringListUnitBuilder
						.createWithIdentifier("ListDah")
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
		CodeUnit cu = VarSingletonUnitBuilder
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

	@org.junit.jupiter.api.Test
	void GenericBuilderTest() throws IOException {
		CodeUnit cu = GenericUnitBuilder
				.createWithIdentifier("ClassWithGenerics")
				.end();

		System.out.println(cu);

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
		//String fileName = (String) cu.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();
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