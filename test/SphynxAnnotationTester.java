/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 25.12.17 23:32
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import sphynx.visitors.JavaClassAnnotationVisitor;

import java.io.File;
import java.io.FileNotFoundException;

public class SphynxAnnotationTester {
	File testFile = new File("resources/ReferencePOJO.java");

	@org.junit.jupiter.api.Test
	void AnnotationReadTest() {
		CompilationUnit cu = null;
		try {
			cu = JavaParser.parse(testFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		new JavaClassAnnotationVisitor().visit(cu, null);
	}
}
