/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 12.02.18 12:45
 */

package amber.parser;

import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableParams;
import amber.model.AnnotationModel;
import amber.model.AnnotationType;
import cherry.model.*;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.lang.annotation.Annotation;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConstructorAnnotationParser extends AnnotationParser {
	public void parseCodeUnitAnnotation(ConstructorDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new ConstructorCodeUnit(CodeUnitType.CONSTRUCTOR);

			model.setDefaultCodeUnit(cu);

			setIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseVariableParamsAnnotation(declaration, model);

			models.add(model);
		});
	}

	private void parseVariableModifierAnnotation(ConstructorDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(VariableModifier.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseVariableParamsAnnotation(ConstructorDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(VariableParams.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_PARAMETERS);
		} else {
			model.getDefaultCodeUnit().addSubCodeUnits(createMethodParamCodeUnits(declaration));
		}
	}

	public void parseFixedCodeUnitAnnotation(ConstructorDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(FixedCodeUnit.class);
		anno.ifPresent( annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();
			CodeUnit constructorCodeUnit = createConstructorCodeUnitFromDeclaration(declaration);

			cu.addSubCodeUnit(constructorCodeUnit);
		});
	}

	private CodeUnit createConstructorCodeUnitFromDeclaration(ConstructorDeclaration declaration) {
		String name = declaration.getName().getIdentifier();

		CodeUnit codeUnit = CodeUnitBuilder
				.createWithIdentifier(name)
				.setCodeUnitType(CodeUnitType.CONSTRUCTOR)
				.withModifiers(getModifier(declaration))
				.end();

		codeUnit.addSubCodeUnits(createMethodParamCodeUnits(declaration));
		codeUnit.addSubCodeUnit(createMethodBodyCodeUnit(declaration));

		return codeUnit;
	}

	private CodeUnit createMethodBodyCodeUnit(ConstructorDeclaration declaration) {
		BlockStmt block = declaration.getBody();
		String methodBody = block.toString();

		methodBody = methodBody.substring(1,methodBody.length()-1).trim();

		CodeUnit codeUnit = CodeUnitBuilder
				.create()
				.setCodeUnitType(CodeUnitType.METHOD_BODY)
				.withMethodBody(methodBody)
				.end();

		codeUnit.addCodeUnitDatum(CodeUnitDatumType.PARENT_CLASS_REF, resolveDeclaringClassName(declaration));

		return codeUnit;
	}

	private List<CodeUnit> createMethodParamCodeUnits(ConstructorDeclaration declaration) {
		return declaration
				.getParameters()
				.stream()
				.map(Parameter::resolve)
				.map(p -> {
							String parameterTypeName = getTypeName(p.getType());
							String declaringClassName = resolveDeclaringClassName(declaration);

							CodeUnit paramCodeUnit = CodeUnitBuilder
									.createWithIdentifier(p.getName())
									.setCodeUnitType(CodeUnitType.METHOD_PARAM)
									.withDataType(getTypeName(p.getType()))
									.end();

							handleClassReference(paramCodeUnit, declaringClassName, parameterTypeName);

							return paramCodeUnit;
						})
				.collect(Collectors.toList());
	}

	private String resolveDeclaringClassName(ConstructorDeclaration declaration) {
		return declaration.resolve().declaringType().getQualifiedName();
	}

	private CodeUnitModifier[] getModifier(ConstructorDeclaration cd) {
		EnumSet<Modifier> mods = cd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}
}
