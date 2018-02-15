/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:29
 */

import scarlet.generator.JavaClassGenerator;
import scarlet.model.*;

import java.io.IOException;

class ScarletGeneratorTester {
	private JavaClassFile fileModel;

	@org.junit.jupiter.api.BeforeEach
	void initializeTestJavaClassFileModel() {
		fileModel = new JavaClassFile();
		fileModel.javaClass = new JavaClass();
		fileModel.javaClass.identifier = "TestClassName";
		fileModel.javaClass.modifiers.add(JavaModifier.PUBLIC);
		fileModel.javaClass.modifiers.add(JavaModifier.FINAL);

		JavaField field1 = new JavaField();
		field1.identifier = "testField1";
		field1.type = "int";
		field1.modifiers.add(JavaModifier.PUBLIC);
		field1.modifiers.add(JavaModifier.STATIC);

		JavaField field2 = new JavaField();
		field2.identifier = "testField2";
		field2.type = "java.lang.String";
		field2.modifiers.add(JavaModifier.PRIVATE);

		fileModel.javaClass.fields.add(field1);
		fileModel.javaClass.fields.add(field2);

		JavaMethod method1 = new JavaMethod();
		method1.identifier = "testMethod1";
		method1.returnType = "int";
		method1.modifiers.add(JavaModifier.PUBLIC);
		method1.modifiers.add(JavaModifier.STATIC);
		method1.body.setContentFromString("return this.testField1");

		JavaMethod method2 = new JavaMethod();
		method2.identifier = "testMethod2";
		method2.returnType = "void";
		method2.modifiers.add(JavaModifier.PRIVATE);

		JavaMethodParameter param1 = new JavaMethodParameter();
		param1.identifier = "testParam1";
		param1.type = "java.lang.String";

		JavaMethodParameter param2 = new JavaMethodParameter();
		param2.identifier = "testParam2";
		param2.type = "int";

		method2.parameters.add(param1);
		method2.parameters.add(param2);
		method2.body.setContentFromString("System.out.println(\"Hello, World!\")");

		fileModel.javaClass.methods.add(method1);
		fileModel.javaClass.methods.add(method2);
	}

	@org.junit.jupiter.api.Test
	void generateClassFromClassFile() {
		JavaClassGenerator testGenerator = new JavaClassGenerator();

		try {
			testGenerator.generateJavaFileFromModel(fileModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}