/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 01:10
 */
package sphynx.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.ubgenerator.UBMethodSpec;
import sphynx.ubmodel.UnitBuilderModel;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitType;

import java.util.Optional;

public class JavaClassAnnotationVisitor extends VoidVisitorAdapter<UnitBuilderModel> {
	@Override
	public void visit(ClassOrInterfaceDeclaration declaration, UnitBuilderModel model) {
		CodeUnit cu = new CodeUnit(CodeUnitType.CLASS);

		boolean hasCodeUnitAnnotation = ParseCodeUnitAnnotiation(declaration, model);

		//
		if(hasCodeUnitAnnotation) {
			//AddSubCodeUnitMethod(model);
			ParseVariableModifierAnnotation(declaration, model);
		}
	}

	private boolean ParseCodeUnitAnnotiation(ClassOrInterfaceDeclaration declaration, UnitBuilderModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.CodeUnit.class);
		if(ae.isPresent()) {
			AnnotationExpr anno = ae.get();

			String identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();

			model.setIdentifier(identifier);
			return true;
		}

		return false;
	}

	//TODO Move to FieldAnnotationVisitor
	private void ParseVariableTypeAnnotation(ClassOrInterfaceDeclaration declaration, UnitBuilderModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.VariableType.class);
		if(ae.isPresent()) {
			model.addBuilderMethod(UBMethodSpec.WITH_DATA_TYPE);
		}
	}

	private void ParseVariableModifierAnnotation(ClassOrInterfaceDeclaration declaration, UnitBuilderModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.VariableModifier.class);
		if(ae.isPresent()) {
			model.addBuilderMethod(UBMethodSpec.WITH_MODIFIERS);
		}
	}
}
