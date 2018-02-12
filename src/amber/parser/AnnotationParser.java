/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */

package amber.parser;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.resolution.types.ResolvedType;
import amber.model.AnnotationModel;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AnnotationParser {
	AnnotationParser()  { }
	void setIdentifier(AnnotationModel model, AnnotationExpr anno) {
			String identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();

			model.setIdentifier(identifier);
	}

	Class getClazz(ResolvedType rt) {
		String type = "";
		if(rt.isPrimitive()) {
			type = rt.describe();
		} else if(rt.isReferenceType()) {
			type = rt.asReferenceType().getQualifiedName();
		}

		return JavaClassMapper.className(type);
	}
}
