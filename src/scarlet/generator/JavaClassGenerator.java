/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 24.12.17 00:36
 */
package scarlet.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.squareup.javapoet.*;
import scarlet.model.*;
import scarlet.visitors.JavaFieldVisitor;
import scarlet.visitors.JavaClassVisitor;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaClassGenerator {
	public void generateClassFromClassFile(File file) throws IOException {
		CompilationUnit cu = JavaParser.parse(file);
		JavaClassFile fm = new JavaClassFile();

		new JavaClassVisitor().visit(cu, fm);
		new JavaFieldVisitor().visit(cu, fm.model);

		generateJavaFileFromModel(fm);
	}

	public void generateJavaFileFromModel(JavaClassFile fm) throws IOException {
		JavaClass cm = fm.model;
		Modifier[] classModifiers = getModifiers(cm.modifiers);
		List<FieldSpec> fieldSpecs = generateFieldSpecs(cm.fields);
		List<MethodSpec> methodSpecs = generateMethodSpecs(cm.methods);
		List<MethodSpec> constructorSpecs = generateConstructorSpecs(cm.constructors);

		TypeSpec generatedClass = TypeSpec.classBuilder(cm.identifier)
				.addModifiers(classModifiers)
				.addFields(fieldSpecs)
				.addMethods(methodSpecs)
				.addMethods(constructorSpecs)
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
		String name = field.identifier;
		Modifier[] modifiers = getModifiers(field.modifiers);

		return FieldSpec
				.builder(ArrayTypeName.of(type), name, modifiers)
				.build();
	}

	private FieldSpec generateFieldSpec(JavaField field) {
		TypeName type = JavaPoetTypeMapper.typeName(field.type);
		String name = field.identifier;
		Modifier[] modifiers = getModifiers(field.modifiers);

		return FieldSpec
				.builder(type, name, modifiers)
				.build();
	}

	private Modifier[] getModifiers(Set<JavaModifier> modifiers) {
		return modifiers.stream()
				.map(m -> Modifier.valueOf(m.name()))
				.collect(Collectors.toList())
				.toArray(new Modifier[modifiers.size()]);
	}

	private List<MethodSpec> generateMethodSpecs(List<JavaMethod> javaMethods) {
		return javaMethods.stream()
				.map(this::generateMethodSpec)
				.collect(Collectors.toList());
	}

	private List<MethodSpec> generateConstructorSpecs(List<JavaConstructor> javaConstructors) {
		return javaConstructors.stream()
				.map(this::generateConstructorSpec)
				.collect(Collectors.toList());
	}

	private MethodSpec generateMethodSpec(JavaMethod method) {
		Modifier[] modifiers = getModifiers(method.modifiers);
		TypeName returnType = JavaPoetTypeMapper.typeName(method.returnType);
		String name = method.identifier;
		String codeBody = method.body.toString() + "\n";

		List<ParameterSpec> params = method.parameters
				.stream()
				.map(this::generateParameterSpec)
				.collect(Collectors.toList());

		return MethodSpec
				.methodBuilder(name)
				.addModifiers(modifiers)
				.addParameters(params)
				.addCode(codeBody)
				.returns(returnType)
				.build();
	}

	private MethodSpec generateConstructorSpec(JavaConstructor constructor) {
		Modifier[] modifiers = getModifiers(constructor.modifiers);
		String codeBody = constructor.body.toString() + "\n";

		List<ParameterSpec> params = constructor.parameters
				.stream()
				.map(this::generateParameterSpec)
				.collect(Collectors.toList());

		return MethodSpec.constructorBuilder()
				.addModifiers(modifiers)
				.addParameters(params)
				.addCode(codeBody)
				.build();
	}

	private ParameterSpec generateParameterSpec(JavaMethodParameter javaMethodParameter) {
		return ParameterSpec
				.builder(JavaPoetTypeMapper.typeName(javaMethodParameter.type), javaMethodParameter.identifier)
				.build();
	}
}
