/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.02.18 13:56
 */
package amber.visitor;

import amber.model.AnnotationModel;
import amber.parser.ConstructorAnnotationParser;
import amber.parser.MethodAnnotationParser;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaFixedConstructorAnnotationVisitor extends VoidVisitorAdapter<AnnotationModel> {
	@Override
	public void visit(ConstructorDeclaration declaration, AnnotationModel model) {
		new ConstructorAnnotationParser().parseFixedCodeUnitAnnotation(declaration, model);
	}
}
