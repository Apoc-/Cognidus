/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:35
 */
package scarlet.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import scarlet.model.JavaClass;
import scarlet.model.JavaClassFile;
import scarlet.model.JavaModifier;

import java.util.Set;
import java.util.stream.Collectors;

public class JavaClassVisitor extends VoidVisitorAdapter<JavaClassFile> {
	@Override
	public void visit(ClassOrInterfaceDeclaration coid, JavaClassFile classFileModel) {
		JavaClass classModel = new JavaClass();

		classModel.identifier = getClassName(coid);
		classModel.modifiers.addAll(getClassModifiers(coid));

		classFileModel.model = classModel;
	}

	private String getClassName(ClassOrInterfaceDeclaration coid) {
		return coid.getNameAsString();
	}

	private Set<JavaModifier> getClassModifiers(ClassOrInterfaceDeclaration coid) {
		return coid.getModifiers()
				.stream()
				.map(modifier -> JavaModifier.valueOf(modifier.name()))
				.collect(Collectors.toSet());
	}
}
