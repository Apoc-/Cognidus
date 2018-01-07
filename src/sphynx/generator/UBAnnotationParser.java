/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 13:34
 */

package sphynx.generator;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitDatumType;
import sphynx.unitmodel.CodeUnitModifier;
import sphynx.unitmodel.CodeUnitType;
import sphynx.visitors.JavaFixedFieldAnnotationVisitor;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UBAnnotationParser {
	private static UBAnnotationParser instance;
	public static UBAnnotationParser getInstance() {
		if(instance == null)
			instance = new UBAnnotationParser();

		return instance;
	}

	private UBAnnotationParser()  { }

	//public methods
	public void ParseCodeUnitAnnotiation(ClassOrInterfaceDeclaration declaration, List<UBModel> models) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.CodeUnit.class);

		ae.ifPresent(annotationExpr -> {
			UBModel model = new UBModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.CLASS);
			model.setDefaultCodeUnit(cu);

			SetIdentifier(model, annotationExpr);
			AddSubCodeUnitMethod(model);

			ParseVariableModifierAnnotation(declaration, model);
			parseFixedCodeUnitAnnotations(declaration, model);

			models.add(model);
		});
	}

	public void ParseCodeUnitAnnotiation(FieldDeclaration declaration, List<UBModel> models) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.CodeUnit.class);
		ae.ifPresent(annotationExpr -> {
			UBModel model = new UBModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.FIELD);
			model.setDefaultCodeUnit(cu);

			SetIdentifier(model, annotationExpr);

			ParseVariableModifierAnnotation(declaration, model);
			ParseVariableTypeAnnotation(declaration, model);

			models.add(model);
		});
	}

	public void ParseFixedCodeUnitAnnotiation(FieldDeclaration declaration, UBModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.FixedCodeUnit.class);
		ae.ifPresent(annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();

			VariableDeclarator vd = declaration.getVariable(0);

			CodeUnit subCodeUnit = UBCodeUnitBuilder
					.createWithIdentifier(getFieldIdentifier(vd))
					.setCodeUnityType(CodeUnitType.FIELD)
					.withModifiers(getModifier(declaration))
					.withDataType(getFieldType(vd))
					.end();

			cu.addSubCodeUnit(subCodeUnit);
		});
	}


	//private methods
	private void ParseVariableModifierAnnotation(ClassOrInterfaceDeclaration declaration, UBModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.VariableModifier.class);
		if(ae.isPresent()) {
			model.addBuilderMethod(UBMethodSpec.WITH_MODIFIERS);
		} else {
			//add default modifiers
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void ParseVariableModifierAnnotation(FieldDeclaration declaration, UBModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.VariableModifier.class);
		if(ae.isPresent()) {
			model.addBuilderMethod(UBMethodSpec.WITH_MODIFIERS);
		} else {
			//add default modifiers
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void ParseVariableTypeAnnotation(FieldDeclaration declaration, UBModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(sphynx.annotations.VariableType.class);
		if(ae.isPresent()) {
			model.addBuilderMethod(UBMethodSpec.WITH_DATA_TYPE);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, getFieldType(declaration.getVariable(0)));
		}
	}

	private void parseFixedCodeUnitAnnotations(ClassOrInterfaceDeclaration declaration, UBModel model) {
		new JavaFixedFieldAnnotationVisitor().visit(declaration, model);
	}

	private CodeUnitModifier[] getModifier(ClassOrInterfaceDeclaration cd) {
		EnumSet<Modifier> mods = cd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}

	private CodeUnitModifier[] getModifier(FieldDeclaration fd) {
		EnumSet<Modifier> mods = fd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}

	private String getFieldIdentifier(VariableDeclarator vd) {
		return vd.getNameAsString();
	}

	private void SetIdentifier(UBModel model, AnnotationExpr anno) {
			String identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();

			model.setIdentifier(identifier);
	}

	private void AddSubCodeUnitMethod(UBModel model) {
		model.addBuilderMethod(UBMethodSpec.WITH_SUB_CODEUNIT);
	}

	//TODO: Add array support
	private boolean isArrayType(Type t) {
		return t instanceof ArrayType;
	}

	private Class getFieldType(VariableDeclarator vd) {
		TypeSolver ts = new ReflectionTypeSolver();
		JavaParserFacade pf = JavaParserFacade.get(ts);
		ResolvedType t = pf.getType(vd);
		String type = "";
		if(t.isPrimitive()) {
			type = t.describe();
		} else if(t.isReferenceType()) {
			type = t.asReferenceType().getQualifiedName();
		}

		return JavaLangClassMapper.className(type);
	}
}
