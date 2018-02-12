/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:24
 */
import cherry.generator.BuilderGenerator;

class CherryGeneratorTester {
	@org.junit.jupiter.api.Test
	void GenerateReferencePOJOBuilders() {
		String targetPath = "src-generated";
		String sourcePath = "resources/ReferencePOJO.java";
		String targetPackage = "cherry.generated.ReferencePOJO";
		BuilderGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}

	@org.junit.jupiter.api.Test
	void GenerateReferenceClazzBuilders() {
		String targetPath = "src-generated";
		String sourcePath = "resources/ReferenceClazz.java";
		String targetPackage = "cherry.generated.ReferenceClazz";
		BuilderGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}
}