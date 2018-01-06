/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:05
 */

package sphynx.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import sphynx.visitors.JavaClassAnnotationVisitor;
import sphynx.visitors.JavaFieldAnnotationVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UBGenerator {
	private static UBGenerator instance;
	private List<UBModel> models;

	public static UBGenerator getInstance() {
		if(instance == null)
			instance = new UBGenerator();
		return instance;
	}

	private UBGenerator() {
		models = new ArrayList<>();
	}


	public void generateUnitBuilders(String path) {
		CompilationUnit cu = parseCodeFile(path);
		populateUnitBuilderModels(cu);
	}

	private CompilationUnit parseCodeFile(String path) {
		File codeFile = new File(path);
		CompilationUnit cu = null;

		try {
			cu = JavaParser.parse(codeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return cu;
	}

	private void populateUnitBuilderModels(CompilationUnit cu) {
		//visit classes
		new JavaClassAnnotationVisitor().visit(cu, this.models);

		//visit fields
		new JavaFieldAnnotationVisitor().visit(cu, this.models);
		System.out.println("asd");
	}
}
