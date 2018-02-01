/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 01.02.18 12:45
 */

package amber.parser;

import amber.model.AnnotationModel;
import amber.model.AnnotationType;
import amber.visitor.JavaFixedFieldAnnotationVisitor;
import amber.visitor.JavaFixedMethodAnnotationVisitor;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitModifier;
import cherry.model.CodeUnitType;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassAnnotationParser extends AnnotationParser {
	public void parseCodeUnitAnnotation(ClassOrInterfaceDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);

		anno.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.CLASS);
			model.setDefaultCodeUnit(cu);
			model.addVariabilityAnnotation(AnnotationType.CAN_HAVE_SUBCODEUNITS);

			setIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseFixedCodeUnitAnnotations(declaration, model);

			models.add(model);
		});
	}

	private void parseVariableModifierAnnotation(ClassOrInterfaceDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.VariableModifier.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseFixedCodeUnitAnnotations(ClassOrInterfaceDeclaration declaration, AnnotationModel model) {
		new JavaFixedFieldAnnotationVisitor().visit(declaration, model);
		new JavaFixedMethodAnnotationVisitor().visit(declaration, model);
	}

	private CodeUnitModifier[] getModifier(ClassOrInterfaceDeclaration cd) {
		EnumSet<Modifier> mods = cd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}
}
