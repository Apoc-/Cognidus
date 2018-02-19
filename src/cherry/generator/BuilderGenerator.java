/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:05
 */

package cherry.generator;

import amber.model.AnnotationModel;
import amber.visitor.JavaClassAnnotationVisitor;
import amber.visitor.JavaConstructorAnnotationVisitor;
import amber.visitor.JavaFieldAnnotationVisitor;
import amber.visitor.JavaMethodAnnotationVisitor;
import cherry.model.CodeUnit;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import violet.Logger;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator {
	private static BuilderGenerator instance;
	private List<AnnotationModel> models;

	public static BuilderGenerator getInstance() {
		if(instance == null)
			instance = new BuilderGenerator();
		return instance;
	}

	private BuilderGenerator() {

	}

	public void generateUnitBuilders(String sourcePath, String targetPath, String targetPackage) {
		this.models = new ArrayList<>();
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
			Logger.console().logError(e.getMessage());
		}

		return cu;
	}

	private void populateAnnotationModels(CompilationUnit cu) {
		new JavaClassAnnotationVisitor().visit(cu, this.models);
		new JavaFieldAnnotationVisitor().visit(cu, this.models);
		new JavaMethodAnnotationVisitor().visit(cu, this.models);
		new JavaConstructorAnnotationVisitor().visit(cu, this.models);
	}

	private void generateUnitBuilder(AnnotationModel model, String targetPath, String targetPackage) {
		BuilderMethodFactory bmf = new BuilderMethodFactory(model, targetPackage);
		String builderClassIdentifier = model.getIdentifier() + "UnitBuilder";
		Class codeUnitClass = getCodeUnitClass(model.getDefaultCodeUnit());
		List<BuilderMethodType> builderMethodTypeList = BuilderMethodTypeProvider.getMethodTypeList(model.getDefaultCodeUnit().getType());

		List<MethodSpec> defaultMethods = builderMethodTypeList
				.stream()
				.map((BuilderMethodType builderMethodType) -> bmf.createForType(builderMethodType, codeUnitClass))
				.collect(Collectors.toList());

		List<MethodSpec> variabilityMethods = model.getVariablityAnnotations()
				.stream()
				.map(anno -> bmf
						.createForType(BuilderMethodMapper.getBuilderMethodType(anno), codeUnitClass))
				.collect(Collectors.toList());

		TypeSpec builderType = TypeSpec
				.classBuilder(builderClassIdentifier)
				.addModifiers(Modifier.PUBLIC)
				.addField(codeUnitClass, "codeUnit", Modifier.PRIVATE)
				.addMethods(defaultMethods)
				.addMethods(variabilityMethods)
				.build();

		JavaFile javaFile = JavaFile.builder(targetPackage, builderType)
				.build();

		try {
			File targetFile = new File(targetPath);
			javaFile.writeTo(targetFile);
		} catch (IOException e) {
			Logger.console().logError(e.getMessage());
		}

		Logger.console().logInfo("Generated " + builderClassIdentifier + " in " + targetPath + " with package " + targetPackage);
	}

	private Class getCodeUnitClass(CodeUnit cu) {
		return cu.getClass();
	}
}
