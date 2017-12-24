/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:35
 */
package wolpertinger.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import wolpertinger.javamodel.JavaClass;
import wolpertinger.javamodel.JavaClassFile;
import wolpertinger.javamodel.JavaModifier;

import java.util.Set;
import java.util.stream.Collectors;

public class JavaClassVisitor extends VoidVisitorAdapter<JavaClassFile> {
	@Override
	public void visit(ClassOrInterfaceDeclaration coid, JavaClassFile classFileModel) {
		JavaClass classModel = new JavaClass();

		classModel.className = getClassName(coid);
		classModel.classModifiers.addAll(getClassModifiers(coid));

		classFileModel.classModel = classModel;
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
