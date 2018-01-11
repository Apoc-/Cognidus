/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:18
 */
package amber.parser.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import amber.parser.AnnotationParser;
import amber.model.AnnotationModel;

import java.util.List;

public class JavaClassAnnotationVisitor extends VoidVisitorAdapter<List<AnnotationModel>> {
	@Override
	public void visit(ClassOrInterfaceDeclaration declaration, List<AnnotationModel> models) {
		AnnotationParser.getInstance().parseCodeUnitAnnotiation(declaration, models);
	}
}
