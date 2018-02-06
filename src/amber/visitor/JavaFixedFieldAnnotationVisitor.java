/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:18
 */
package amber.visitor;

import amber.model.AnnotationModel;
import amber.parser.FieldAnnotationParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaFixedFieldAnnotationVisitor extends VoidVisitorAdapter<AnnotationModel> {
	@Override
	public void visit(FieldDeclaration declaration, AnnotationModel model) {
		new FieldAnnotationParser().parseFixedCodeUnitAnnotation(declaration, model);
	}
}
