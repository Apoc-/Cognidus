/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */

package amber.parser;

import amber.model.AnnotationType;
import amber.visitor.JavaFixedFieldAnnotationVisitor;
import amber.visitor.JavaFixedMethodAnnotationVisitor;
import cherry.model.*;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.resolution.types.ResolvedType;
import amber.model.AnnotationModel;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
