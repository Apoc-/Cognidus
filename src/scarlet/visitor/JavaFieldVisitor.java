/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:35
 */
package scarlet.visitor;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import scarlet.model.JavaField;
import scarlet.model.JavaClass;
import scarlet.model.JavaModifier;

import java.util.Set;
import java.util.stream.Collectors;

public class JavaFieldVisitor extends VoidVisitorAdapter<JavaClass> {
	//Only supports field declaration that declare a single variable
	//No "public int a, b, c;" allowed
	@Override
	public void visit(FieldDeclaration fd, JavaClass classModel) {
		JavaField jfm = new JavaField();
		VariableDeclarator varDeclaration = fd.getVariable(0);

		jfm.identifier = getFieldName(varDeclaration);
		jfm.modifiers.addAll(getFieldModifier(fd));
		jfm.isArray = isArrayType(varDeclaration.getType());
		jfm.type = getFieldType(varDeclaration);

		classModel.fields.add(jfm);
	}

	private String getFieldName(VariableDeclarator vd) {
		return vd.getNameAsString();
	}

	private Set<JavaModifier> getFieldModifier(FieldDeclaration fd) {
		return fd.getModifiers()
				.stream()
				.map(modifier -> JavaModifier.valueOf(modifier.name()))
				.collect(Collectors.toSet());
	}

	private String getFieldType(VariableDeclarator vd) {
		return vd.getType().getElementType().asString();
	}

	private boolean isArrayType(Type t) {
		return t instanceof ArrayType;
	}
}
