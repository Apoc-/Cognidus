/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:24
 */
import cherry.generator.BuilderGenerator;

class CherryGeneratorTester {
	@org.junit.jupiter.api.Test
	void GenReferenceClazzBuilders() {
		String targetPath = "src-generated";
		String sourcePath = "resources/ReferenceClass.java";
		String targetPackage = "cherry.generated.ReferenceClass";
		BuilderGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}

	@org.junit.jupiter.api.Test
	void GenSingletonBuilder() {
		String targetPath = "src-generated";
		String sourcePath = "resources/SingletonClass.java";
		String targetPackage = "cherry.generated.SingletonClass";
		BuilderGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}

	@org.junit.jupiter.api.Test
	void ExampleTest() {
		String targetPath = "src-generated";
		String sourcePath = "resources/ExampleRef.java";
		String targetPackage = "cherry.generated.ExampleRef";
		BuilderGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}
}