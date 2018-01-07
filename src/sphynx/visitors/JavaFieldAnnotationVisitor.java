/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 13:28
 */
package sphynx.visitors;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.generator.*;

import java.util.List;

public class JavaFieldAnnotationVisitor extends VoidVisitorAdapter<List<UBModel>> {
	@Override
	public void visit(FieldDeclaration declaration, List<UBModel> models) {
		UBAnnotationParser.getInstance().ParseCodeUnitAnnotiation(declaration, models);
	}
}
