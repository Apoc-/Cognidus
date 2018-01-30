/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:05
 */

package cherry.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.SerializationUtils;
import amber.model.AnnotationModel;
import cherry.model.CodeUnit;
import amber.visitor.JavaClassAnnotationVisitor;
import amber.visitor.JavaFieldAnnotationVisitor;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator {
	private static BuilderGenerator instance;
	private final List<AnnotationModel> models;

	public static BuilderGenerator getInstance() {
		if(instance == null)
			instance = new BuilderGenerator();
		return instance;
	}

	private BuilderGenerator() {
		models = new ArrayList<>();
	}

	public void generateUnitBuilders(String sourcePath, String targetPath, String targetPackage) {
		CompilationUnit cu = parseCodeFile(sourcePath);
		populateAnnotationModels(cu);

		models.forEach(m -> generateUnitBuilder(m, targetPath, targetPackage));
	}

	private CompilationUnit parseCodeFile(String path) {
		File codeFile = new File(path);
		CompilationUnit cu = null;

		try {
			cu = JavaParser.parse(codeFile);

			TypeSolver ts = new ReflectionTypeSolver();
			JavaSymbolSolver jss = new JavaSymbolSolver(ts);
			jss.inject(cu);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return cu;
	}

	private void populateAnnotationModels(CompilationUnit cu) {
		//visit classes
		new JavaClassAnnotationVisitor().visit(cu, this.models);

		//visit fields
		new JavaFieldAnnotationVisitor().visit(cu, this.models);
	}


	//Todo: Move MethodSpec creations to factory
	private void generateUnitBuilder(AnnotationModel model, String targetPath, String targetPackage) {
		String builderClassIdentifier = model.getIdentifier() + "UnitBuilder";
		TypeName builderClassName = ClassName.get(targetPackage, builderClassIdentifier);

		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addStatement("initializeDefaultCodeUnit()")
				.build();

		MethodSpec codeUnitInitializer = generateInitDefCodeUnitMethod(model.getDefaultCodeUnit());

		MethodSpec builderInitializer = BuilderMethodFactory.createForType(BuilderMethodType.CREATE_WITH_IDENTIFIER, builderClassName);
		MethodSpec builderFinalizer = BuilderMethodFactory.createForType(BuilderMethodType.END, builderClassName);

		List<MethodSpec> methods = model.getAnnotationData()
				.stream()
				.map(anno -> BuilderMethodFactory
						.createForType(BuilderMethodMapper.getBuilderMethodType(anno), builderClassName))
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

	//Todo: Talk why THIS should be the solution
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

	/*
	private MethodSpec generateInitDefCodeUnitMethod(CodeUnit sourceCodeUnit) {
		MethodSpec initMethod = MethodSpec.methodBuilder("initializeDefaultCodeUnit")
				.addModifiers(Modifier.PRIVATE)
				.addCode("this.codeUnit = CodeUnitBuilder\n")
				.addCode("\t\t.createWithIdentifier($S)\n", getDatum(CodeUnitDatumType.IDENTIFIER, sourceCodeUnit))
				.addCode("\t\t.withDataType($L)\n", getDatum(CodeUnitDatumType.DATA_TYPE, sourceCodeUnit))
				.addCode(".end();\n")
				.build();

		System.out.println(initMethod.toString());

		return initMethod;
	}

	private String getDatum(CodeUnitDatumType type, CodeUnit cu) {
		CodeUnitDatum cud = cu.getCodeUnitDatum(type);
		if(cud == null)
			return null;

		String literal = "";
		switch(type) {
			case IDENTIFIER:
				literal = (String) cud.getDatumData();
				break;
			case DATA_TYPE:
				literal = (String) cud.getDatumData();
				break;
		}

		return literal;
	}

	/*private void initializeCodeUnitData() {
		CodeUnit defaultCodeUnit = CodeUnitBuilder
				.createWithIdentifier("ident")
				.withDataType(dt)
				.withModifiers()
				.withSubCodeUnits(initializeSubCodeUnitData())
				.end();
	}

	private List<CodeUnit> initializeSubCodeUnitData() {
		List<CodeUnit> subCodeUnits = new LinkedList<CodeUnit>();

		subCodeUnits.add(CodeUnitBuilder
				.createWithIdentifier()
				.withDataType()
				.withModifiers()
				.withSubCodeUnits()
				.end());

		subCodeUnits.add(CodeUnitBuilder
				.createWithIdentifier()
				.withDataType()
				.withModifiers()
				.withSubCodeUnits()
				.end());

		subCodeUnits.add(CodeUnitBuilder
				.createWithIdentifier()
				.withDataType()
				.withModifiers()
				.withSubCodeUnits()
				.end());

		return subCodeUnits;
	}*/
}
