/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 12:00
 */

package CodeUnitBuilderGeneratorTests;

import sphynx.generator.UBGenerator;

public class UBModelGeneratorTests {
	@org.junit.jupiter.api.Test
	void FillModelFromAnnotatedFile() {
		String path = "resources/ReferencePOJO.java";
		UBGenerator.getInstance().generateUnitBuilders(path);
	}
}
