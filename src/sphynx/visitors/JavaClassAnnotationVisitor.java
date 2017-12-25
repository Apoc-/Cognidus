/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 01:10
 */
package sphynx.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.model.Component;
import sphynx.model.ComponentDatum;
import sphynx.model.Composite;

import java.util.Optional;

public class JavaClassAnnotationVisitor extends VoidVisitorAdapter<Composite> {
	@Override
	public void visit(ClassOrInterfaceDeclaration coid, Composite cmps) {
		Component cmpt = new Component();
		cmpt.type = "class";

		Optional<AnnotationExpr> ae = coid.getAnnotationByClass(sphynx.annotations.Component.class);
		if(ae.isPresent()) {
			AnnotationExpr anno = ae.get();

			cmpt.identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();
		}

		ae = coid.getAnnotationByClass(sphynx.annotations.Renamable.class);
		ComponentDatum cd = new ComponentDatum("name", coid.getName().asString());

		if(ae.isPresent()) {
			cd.changeable = true;
		}

		cmpt.componentData.put(cd.type,cd);
		cmps.components.add(cmpt);
	}
}
