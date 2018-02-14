/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */

package amber.parser;

import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.resolution.types.ResolvedType;
import amber.model.AnnotationModel;

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

	String getTypeName(ResolvedType rt) {
		String type = "";
		if(rt.isPrimitive()) {
			type = rt.describe();
		} else if(rt.isReferenceType()) {
			type = rt.asReferenceType().getQualifiedName();
		}

		return type;
	}

	void handleClassReference(CodeUnit codeUnit, String declaringClassName, String typeName) {
		if(declaringClassName.equals(typeName)) {
			codeUnit.addCodeUnitDatum(CodeUnitDatumType.PARENT_CLASS_REF, declaringClassName);
		}
	}
}
