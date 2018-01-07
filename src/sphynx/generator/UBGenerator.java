/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:05
 */

package sphynx.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.SerializationUtils;
import sphynx.unitmodel.CodeUnit;
import sphynx.visitors.JavaClassAnnotationVisitor;
import sphynx.visitors.JavaFieldAnnotationVisitor;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Tasks Todo
// Transformator to JavaCodeModel
// Arrays
// Generics
// Methods
public class UBGenerator {
	private static UBGenerator instance;
	private final List<UBModel> models;

	public static UBGenerator getInstance() {
		if(instance == null)
			instance = new UBGenerator();
		return instance;
	}

	private UBGenerator() {
		models = new ArrayList<>();
	}

	public void generateUnitBuilders(String sourcePath, String targetPath, String targetPackage) {
		CompilationUnit cu = parseCodeFile(sourcePath);
		populateUnitBuilderModels(cu);

		models.forEach(m -> generateUnitBuilder(m, targetPath, targetPackage));
	}

	private CompilationUnit parseCodeFile(String path) {
		File codeFile = new File(path);
		CompilationUnit cu = null;

		try {
			cu = JavaParser.parse(codeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return cu;
	}

	private void populateUnitBuilderModels(CompilationUnit cu) {
		//visit classes
		new JavaClassAnnotationVisitor().visit(cu, this.models);

		//visit fields
		new JavaFieldAnnotationVisitor().visit(cu, this.models);
	}


	//Todo: Move MethodSpec creations to factory
	private void generateUnitBuilder(UBModel model, String targetPath, String targetPackage) {
		String builderClassIdentifier = model.getIdentifier() + "UnitBuilder";
		TypeName builderClassName = ClassName.get(targetPackage, builderClassIdentifier);

		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addStatement("initializeDefaultCodeUnit()")
				.build();

		MethodSpec codeUnitInitializer = generateInitDefCodeUnitMethod(model.getDefaultCodeUnit());

		MethodSpec builderInitializer = UBMethodSpecFactory.createForType(UBMethodSpec.CREATE_WITH_IDENTIFIER, builderClassName);
		MethodSpec builderFinalizer = UBMethodSpecFactory.createForType(UBMethodSpec.END, builderClassName);

		List<MethodSpec> methods = model.getBuilderMethods()
				.stream()
				.map(ubm -> UBMethodSpecFactory.createForType(ubm, builderClassName))
				.collect(Collectors.toList());

		TypeSpec builderType = TypeSpec
				.classBuilder(builderClassIdentifier)
				.addModifiers(Modifier.PUBLIC)
				.addField(CodeUnit.class, "codeUnit", Modifier.PRIVATE)
				.addMethod(constructor)
				.addMethod(codeUnitInitializer)
				.addMethod(builderInitializer)
				.addMethod(builderFinalizer)
				.addMethods(methods)
				.build();

		JavaFile javaFile = JavaFile.builder(targetPackage, builderType)
				.build();

		try {
			File targetFile = new File(targetPath);
			javaFile.writeTo(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Generated " + builderClassIdentifier + " in " + targetPath + " with package " + targetPackage);
	}

	//Todo: Find better readable solutions
	private MethodSpec generateInitDefCodeUnitMethod(CodeUnit sourceCodeUnit) {
		byte[] serializedCodeUnit = SerializationUtils.serialize(sourceCodeUnit);

		String codeUnitArrayLiteral = Arrays
				.toString(serializedCodeUnit)
				.replace("[","{")
				.replace("]","}");

		return MethodSpec.methodBuilder("initializeDefaultCodeUnit")
				.addComment("Initializes this builder's data with default data encoded into a byte[]")
				.addModifiers(Modifier.PRIVATE)
				.addStatement("byte[] serializedCodeUnit = new byte[] $L", codeUnitArrayLiteral)
				.addStatement("this.codeUnit = $T.deserialize(serializedCodeUnit)", SerializationUtils.class)
				.build();
	}
}
