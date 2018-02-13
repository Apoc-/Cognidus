/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 12.02.18 12:45
 */

package amber.parser;

import amber.annotations.FixedCodeUnit;
import amber.model.AnnotationModel;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitBuilder;
import cherry.model.CodeUnitModifier;
import cherry.model.CodeUnitType;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConstructorAnnotationParser extends AnnotationParser {
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

		return CodeUnitBuilder
				.create()
				.setCodeUnitType(CodeUnitType.METHOD_BODY)
				.withMethodBody(methodBody)
				.end();
	}

	private List<CodeUnit> createMethodParamCodeUnits(ConstructorDeclaration declaration) {
		return declaration
				.getParameters()
				.stream()
				.map(Parameter::resolve)
				.map(p ->
						CodeUnitBuilder
								.createWithIdentifier(p.getName())
								.setCodeUnitType(CodeUnitType.METHOD_PARAM)
								.withDataType(getTypeName(p.getType()))
								.end())
				.collect(Collectors.toList());
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
