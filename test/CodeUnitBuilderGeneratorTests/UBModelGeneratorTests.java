/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 12:00
 */

package CodeUnitBuilderGeneratorTests;

import sphynx.generator.UBGenerator;

public class UBModelGeneratorTests {
	@org.junit.jupiter.api.Test
	void GenerateJavaFilesFromReference() {
		String targetPath = "src";
		String sourcePath = "resources/ReferencePOJO.java";
		String targetPackage = "sphynx.generated";
		UBGenerator.getInstance().generateUnitBuilders(sourcePath, targetPath, targetPackage);
	}
}
