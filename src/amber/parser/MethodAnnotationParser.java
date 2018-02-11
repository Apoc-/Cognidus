/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 01.02.18 13:21
 */

package amber.parser;

import amber.annotations.FixedCodeUnit;
import amber.annotations.VariableModifier;
import amber.annotations.VariableParams;
import amber.model.AnnotationModel;
import amber.model.AnnotationType;
import cherry.model.*;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.resolution.types.ResolvedType;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodAnnotationParser extends AnnotationParser {
	public void parseCodeUnitAnnotation(MethodDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.METHOD);

			/*CodeUnit defBodyCu = CodeUnitBuilder
					.create()
					.setCodeUnitType(CodeUnitType.METHOD_BODY)
					.withMethodBody("//this is a generated empty body")
					.end();*/

			//cu.addSubCodeUnit(defBodyCu);

			model.setDefaultCodeUnit(cu);

			setIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseVariableParamsAnnotation(declaration, model);

			models.add(model);
		});
	}

	public void parseFixedCodeUnitAnnotation(MethodDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(FixedCodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();
			CodeUnit methodCodeUnit = createMethodCodeUnitFromDeclaration(declaration);

			cu.addSubCodeUnit(methodCodeUnit);
		});
	}

	private void parseVariableModifierAnnotation(MethodDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(VariableModifier.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseVariableParamsAnnotation(MethodDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(VariableParams.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_PARAMETERS);
		} else {
			model.getDefaultCodeUnit().addSubCodeUnits(createMethodParamCodeUnits(declaration));
		}
	}

	private CodeUnit createMethodCodeUnitFromDeclaration(MethodDeclaration declaration) {
		String name = declaration.getName().getIdentifier();

		CodeUnit codeUnit = CodeUnitBuilder
				.createWithIdentifier(name)
				.setCodeUnitType(CodeUnitType.METHOD)
				.withModifiers(getModifier(declaration))
				.withReturnType(resolveMethodReturnType(declaration))
				.end();

		codeUnit.addSubCodeUnits(createMethodParamCodeUnits(declaration));
		codeUnit.addSubCodeUnit(createMethodBodyCodeUnit(declaration));

		return codeUnit;
	}

	private CodeUnit createMethodBodyCodeUnit(MethodDeclaration declaration) {
		String methodBody = "";
		Optional<BlockStmt> bs = declaration.getBody();
		if(bs.isPresent()) {
			methodBody = bs.get().toString();
			methodBody = methodBody.substring(1,methodBody.length()-1).trim();
		}

		return CodeUnitBuilder
				.create()
				.setCodeUnitType(CodeUnitType.METHOD_BODY)
				.withMethodBody(methodBody)
				.end();
	}

	private CodeUnitModifier[] getModifier(MethodDeclaration fd) {
		EnumSet<Modifier> mods = fd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}

	private List<CodeUnit> createMethodParamCodeUnits(MethodDeclaration declaration) {
		return declaration
				.getParameters()
				.stream()
				.map(Parameter::resolve)
				.map(p ->
						CodeUnitBuilder
								.createWithIdentifier(p.getName())
								.setCodeUnitType(CodeUnitType.METHOD_PARAM)
								.withDataType(getClazz(p.getType()))
								.end())
				.collect(Collectors.toList());
	}

	private Class resolveMethodReturnType(MethodDeclaration md) {
		ResolvedType rt = md.resolve().getReturnType();
		return getClazz(rt);
	}
}