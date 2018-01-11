/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:46
 */

package amber.parser;

import amber.model.AnnotationDatum;
import amber.parser.visitor.JavaFixedFieldAnnotationVisitor;
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
import amber.model.AnnotationModel;
import cherry.model.CodeUnitBuilder;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitModifier;
import cherry.model.CodeUnitType;

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

	public void parseCodeUnitAnnotiation(ClassOrInterfaceDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);

		ae.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.CLASS);
			model.setDefaultCodeUnit(cu);
			model.addAnnotationDatum(AnnotationDatum.CAN_HAVE_SUBCODEUNITS);

			SetIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseFixedCodeUnitAnnotations(declaration, model);

			models.add(model);
		});
	}

	public void parseCodeUnitAnnotiation(FieldDeclaration declaration, List<AnnotationModel> models) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.CodeUnit.class);
		ae.ifPresent(annotationExpr -> {
			AnnotationModel model = new AnnotationModel();
			CodeUnit cu = new CodeUnit(CodeUnitType.FIELD);
			model.setDefaultCodeUnit(cu);

			SetIdentifier(model, annotationExpr);

			parseVariableModifierAnnotation(declaration, model);
			parseVariableTypeAnnotation(declaration, model);

			models.add(model);
		});
	}

	public void parseFixedCodeUnitAnnotiation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.FixedCodeUnit.class);
		ae.ifPresent(annotationExpr -> {
			CodeUnit cu = model.getDefaultCodeUnit();

			VariableDeclarator vd = declaration.getVariable(0);

			CodeUnit subCodeUnit = CodeUnitBuilder
					.createWithIdentifier(getFieldIdentifier(vd))
					.setCodeUnityType(CodeUnitType.FIELD)
					.withModifiers(getModifier(declaration))
					.withDataType(getFieldType(vd))
					.end();

			cu.addSubCodeUnit(subCodeUnit);
		});
	}

	private void parseVariableModifierAnnotation(ClassOrInterfaceDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.VariableModifier.class);
		if(ae.isPresent()) {
			model.addAnnotationDatum(AnnotationDatum.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseVariableModifierAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.VariableModifier.class);
		if(ae.isPresent()) {
			model.addAnnotationDatum(AnnotationDatum.VARIABLE_MODIFIERS);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.MODIFIER, getModifier(declaration));
		}
	}

	private void parseVariableTypeAnnotation(FieldDeclaration declaration, AnnotationModel model) {
		Optional<AnnotationExpr> ae = declaration.getAnnotationByClass(amber.annotations.VariableType.class);
		if(ae.isPresent()) {
			model.addAnnotationDatum(AnnotationDatum.VARIABLE_DATATYPE);
		} else {
			model.getDefaultCodeUnit().addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, getFieldType(declaration.getVariable(0)));
		}
	}

	private void parseFixedCodeUnitAnnotations(ClassOrInterfaceDeclaration declaration, AnnotationModel model) {
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

	private void SetIdentifier(AnnotationModel model, AnnotationExpr anno) {
			String identifier = anno
					.asSingleMemberAnnotationExpr()
					.getMemberValue()
					.asStringLiteralExpr()
					.getValue();

			model.setIdentifier(identifier);
	}

	private void addCanHaveSubcodeUnitAnnotationDatum(AnnotationModel model) {
		model.addAnnotationDatum(AnnotationDatum.CAN_HAVE_SUBCODEUNITS);
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

		return JavaClassMapper.className(type);
	}
}
