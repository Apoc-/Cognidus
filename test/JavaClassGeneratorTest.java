/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:43
 */

import wolpertinger.generator.JavaClassGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class JavaClassGeneratorTest {
	JavaClassGenerator testGenerator;
	File testFile;

	@org.junit.jupiter.api.BeforeEach
	void setUp() {
		testGenerator = new JavaClassGenerator();
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		testFile = new File("resources/ReferencePOJO.java");
	}

	@org.junit.jupiter.api.AfterEach
	void tearDown() {
		testGenerator = null;
		testFile = null;
	}

	@org.junit.jupiter.api.Test
	void generateClassFromClassFile() {
		try {
			testGenerator.GenerateClassFromClassFile(testFile);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}