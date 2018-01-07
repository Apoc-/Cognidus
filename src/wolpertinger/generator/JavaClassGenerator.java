/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:36
 */
package wolpertinger.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.squareup.javapoet.*;
import wolpertinger.javamodel.JavaClass;
import wolpertinger.javamodel.JavaClassFile;
import wolpertinger.javamodel.JavaField;
import wolpertinger.javamodel.JavaModifier;
import wolpertinger.visitors.JavaFieldVisitor;
import wolpertinger.visitors.JavaClassVisitor;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaClassGenerator {
	public JavaClassGenerator() { }

	public void GenerateClassFromClassFile(File file) throws IOException {
		CompilationUnit cu = JavaParser.parse(file);
		JavaClassFile fm = new JavaClassFile();

		new JavaClassVisitor().visit(cu, fm);
		new JavaFieldVisitor().visit(cu, fm.model);

		GenerateJavaFileFromModel(fm);
	}

	private void GenerateJavaFileFromModel(JavaClassFile fm) throws IOException {
		JavaClass cm = fm.model;
		Modifier[] classModifiers = getModifierArrayFromSet(cm.modifiers);
		List<FieldSpec> fieldSpecs = generateFieldSpecs(cm.fields);

		TypeSpec generatedClass = TypeSpec.classBuilder(cm.name)
				.addModifiers(classModifiers)
				.addFields(fieldSpecs)
				.build();

		JavaFile javaFile = JavaFile.builder("com.example.helloworld", generatedClass)
				.build();

		javaFile.writeTo(System.out);
	}

	private List<FieldSpec> generateFieldSpecs(List<JavaField> modelFields) {
		List<FieldSpec> fieldSpecs = new LinkedList<>();
		modelFields.forEach(field -> {
			FieldSpec fs;

			if(field.isArray) {
				fs = generateArrayFieldSpec(field);
			} else {
				fs = generateFieldSpec(field);
			}

			fieldSpecs.add(fs);
		});

		return fieldSpecs;
	}

	private FieldSpec generateArrayFieldSpec(JavaField field) {
		TypeName type = JavaPoetTypeMapper.typeName(field.type);
		String name = field.name;
		Modifier[] modifiers = getModifierArrayFromSet(field.modifiers);

		return FieldSpec
				.builder(ArrayTypeName.of(type), name, modifiers)
				.build();
	}

	private FieldSpec generateFieldSpec(JavaField field) {
		TypeName type = JavaPoetTypeMapper.typeName(field.type);
		String name = field.name;
		Modifier[] modifiers = getModifierArrayFromSet(field.modifiers);

		return FieldSpec
				.builder(type, name, modifiers)
				.build();
	}

	private Modifier[] getModifierArrayFromSet(Set<JavaModifier> modifiers) {
		return modifiers.stream()
				.map(m -> Modifier.valueOf(m.name()))
				.collect(Collectors.toList())
				.toArray(new Modifier[modifiers.size()]);
	}
}
