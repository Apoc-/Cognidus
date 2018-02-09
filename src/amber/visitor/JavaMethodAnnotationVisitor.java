/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.02.18 13:56
 */
package amber.visitor;

import amber.model.AnnotationModel;
import amber.parser.FieldAnnotationParser;
import amber.parser.MethodAnnotationParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class JavaMethodAnnotationVisitor extends VoidVisitorAdapter<List<AnnotationModel>> {
	@Override
	public void visit(MethodDeclaration declaration, List<AnnotationModel> models) {
		new MethodAnnotationParser().parseCodeUnitAnnotation(declaration, models);
	}
}
