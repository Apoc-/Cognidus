/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 09.02.18 17:52
 */
package amber.visitor;

import amber.model.AnnotationModel;
import amber.parser.ConstructorAnnotationParser;
import amber.parser.MethodAnnotationParser;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class JavaConstructorAnnotationVisitor extends VoidVisitorAdapter<List<AnnotationModel>> {
	@Override
	public void visit(ConstructorDeclaration declaration, List<AnnotationModel> models) {
		new ConstructorAnnotationParser().parseCodeUnitAnnotation(declaration, models);
	}
}
