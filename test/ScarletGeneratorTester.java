/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:29
 */

import scarlet.generator.JavaClassGenerator;

import java.io.File;
import java.io.IOException;

class ScarletGeneratorTester {
	@org.junit.jupiter.api.Test
	void generateClassFromClassFile() {
		JavaClassGenerator testGenerator = new JavaClassGenerator();
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File testFile = new File("resources/ReferencePOJO.java");

		try {
			testGenerator.GenerateClassFromClassFile(testFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}