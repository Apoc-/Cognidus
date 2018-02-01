/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:18
 */
package amber.visitor;

import amber.parser.FieldAnnotationParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import amber.model.AnnotationModel;
import amber.parser.AnnotationParser;

import java.util.List;

public class JavaFieldAnnotationVisitor extends VoidVisitorAdapter<List<AnnotationModel>> {
	@Override
	public void visit(FieldDeclaration declaration, List<AnnotationModel> models) {
		new FieldAnnotationParser().parseCodeUnitAnnotation(declaration, models);
	}
}
