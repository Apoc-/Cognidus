/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 01:10
 */
package sphynx.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.generator.UBAnnotationParser;
import sphynx.generator.UBModel;

import java.util.List;

public class JavaClassAnnotationVisitor extends VoidVisitorAdapter<List<UBModel>> {
	@Override
	public void visit(ClassOrInterfaceDeclaration declaration, List<UBModel> models) {
		UBAnnotationParser.getInstance().ParseCodeUnitAnnotiation(declaration, models);
	}
}
