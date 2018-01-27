/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 12.01.18 00:33
 */
package amber.visitor;

import amber.model.AnnotationModel;
import amber.parser.AnnotationParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaFixedMethodAnnotationVisitor extends VoidVisitorAdapter<AnnotationModel> {
	@Override
	public void visit(MethodDeclaration declaration, AnnotationModel model) {
		AnnotationParser.getInstance().parseFixedCodeUnitAnnotiation(declaration, model);
	}
}
