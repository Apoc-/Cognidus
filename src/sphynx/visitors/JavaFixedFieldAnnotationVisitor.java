/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:45
 */
package sphynx.visitors;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.generator.*;

public class JavaFixedFieldAnnotationVisitor extends VoidVisitorAdapter<UBModel> {
	@Override
	public void visit(FieldDeclaration declaration, UBModel model) {
		UBAnnotationParser.getInstance().ParseFixedCodeUnitAnnotiation(declaration, model);
	}
}
