/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.01.18 13:28
 */
package sphynx.visitors;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import sphynx.generator.*;
import sphynx.unitmodel.CodeUnit;
import sphynx.unitmodel.CodeUnitModifier;
import sphynx.unitmodel.CodeUnitType;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaFieldAnnotationVisitor extends VoidVisitorAdapter<List<UBModel>> {
	@Override
	public void visit(FieldDeclaration declaration, List<UBModel> models) {
		UBAnnotationParser.getInstance().ParseCodeUnitAnnotiation(declaration, models);
	}
}
