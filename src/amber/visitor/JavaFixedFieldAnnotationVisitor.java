/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:18
 */
package amber.visitor;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import amber.model.AnnotationModel;
import amber.parser.AnnotationParser;

public class JavaFixedFieldAnnotationVisitor extends VoidVisitorAdapter<AnnotationModel> {
	@Override
	public void visit(FieldDeclaration declaration, AnnotationModel model) {
		AnnotationParser.getInstance().parseFixedCodeUnitAnnotiation(declaration, model);
	}
}
