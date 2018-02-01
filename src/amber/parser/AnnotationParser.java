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
	private static AnnotationParser instance;
	public static AnnotationParser getInstance() {
		if(instance == null)
			instance = new AnnotationParser();

		return instance;
	}

	private AnnotationParser()  { }

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

	public void parseCodeUnitAnnotation(FieldDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.FIELD);
			model.setDefaultCodeUnit(cu);

			setIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseVariableTypeAnnotation(declaration, model);
			parseHasGetterAnnotation(declaration, model);
			parseHasSetterAnnotation(declaration, model);

			models.add(model);
		});
	}

	private void parseHasGetterAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.HasGetter.class);
		if(anno.isPresent()) {
			model.addExtensionAnnotation(AnnotationType.HAS_GETTER);
		}
	}

	private void parseHasSetterAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.HasSetter.class);
		if(anno.isPresent()) {
			model.addExtensionAnnotation(AnnotationType.HAS_SETTER);
		}
	}

	public void parseFixedCodeUnitAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.FixedCodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();

			VariableDeclarator vd = declaration.getVariable(0);

			CodeUnit subCodeUnit = CodeUnitBuilder
					.createWithIdentifier(getFieldIdentifier(vd))
					.setCodeUnitType(CodeUnitType.FIELD)
					.withModifiers(getModifier(declaration))
					.withDataType(resolveVariableType(vd))
					.end();

			parseFixedCodeUnitHasGetterAnnotation(declaration, cu);
			parseFixedCodeUnitHasSetterAnnotation(declaration, cu);


			cu.addSubCodeUnit(subCodeUnit);
		});
	}

	public void parseFixedCodeUnitAnnotation(MethodDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.FixedCodeUnit.class);
		anno.ifPresent(annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();
			CodeUnit methodCodeUnit = createMethodCodeUnitFromDeclaration(declaration);

			cu.addSubCodeUnit(methodCodeUnit);
		});
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

	private void parseVariableModifierAnnotation(ClassOrInterfaceDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.VariableModifier.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseVariableModifierAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.VariableModifier.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseFixedCodeUnitHasGetterAnnotation(FieldDeclaration declaration, CodeUnit cu) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.HasGetter.class);
		if(anno.isPresent()) {
			VariableDeclarator vd = declaration.getVariable(0);
			Class type = resolveVariableType(vd);
			String identifier = getFieldIdentifier(vd);

			CodeUnit method = CodeUnitBuilderUtils.createGetterCodeUnit(identifier, type);

			cu.addSubCodeUnit(method);
		}
	}

	private void parseFixedCodeUnitHasSetterAnnotation(FieldDeclaration declaration, CodeUnit cu) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.HasSetter.class);
		if(anno.isPresent()) {
			VariableDeclarator vd = declaration.getVariable(0);
			String identifier = getFieldIdentifier(vd);
			Class type = resolveVariableType(vd);

			CodeUnit method = CodeUnitBuilderUtils.createSetterCodeUnit(identifier, type);

			cu.addSubCodeUnit(method);
		}
	}

	private void parseVariableTypeAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> anno = declaration.getAnnotationByClass(amber.annotations.VariableType.class);
		if(anno.isPresent()) {
			model.addVariabilityAnnotation(AnnotationType.VARIABLE_DATATYPE);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, resolveVariableType(declaration.getVariable(0)));
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

	private CodeUnitModifier[] getModifier(FieldDeclaration fd) {
		EnumSet<Modifier> mods = fd.getModifiers();

		return mods
				.stream()
				.map(modifier -> CodeUnitModifier.valueOf(modifier.name()))
				.collect(Collectors.toList())
				.toArray(new CodeUnitModifier[mods.size()]);
	}

	private CodeUnitModifier[] getModifier(MethodDeclaration fd) {
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

	private void setIdentifier(AnnotationModel model, AnnotationExpr anno) {
			String identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();

			model.setIdentifier(identifier);
	}

	private void addCanHaveSubcodeUnitAnnotationDatum(AnnotationModel model) {
		model.addVariabilityAnnotation(AnnotationType.CAN_HAVE_SUBCODEUNITS);
	}

	private Class resolveVariableType(VariableDeclarator vd) {
		ResolvedType rt = vd.resolve().getType();
		return getClazz(rt);
	}

	private Class resolveMethodReturnType(MethodDeclaration md) {
		ResolvedType rt = md.resolve().getReturnType();
		return getClazz(rt);
	}

	private Class getClazz(ResolvedType rt) {
		String type = "";
		if(rt.isPrimitive()) {
			type = rt.describe();
		} else if(rt.isReferenceType()) {
			type = rt.asReferenceType().getQualifiedName();
		}

		return JavaClassMapper.className(type);
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
}
