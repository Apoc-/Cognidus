/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 22:05
 */

package cherry.generator;

import amber.model.AnnotationModel;
import amber.visitor.JavaClassAnnotationVisitor;
import amber.visitor.JavaFieldAnnotationVisitor;
import amber.visitor.JavaMethodAnnotationVisitor;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitType;
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
			Logger.console().logError(e.getMessage());
		}

		return cu;
	}

	private void populateAnnotationModels(CompilationUnit cu) {
		new JavaClassAnnotationVisitor().visit(cu, this.models);
		new JavaFieldAnnotationVisitor().visit(cu, this.models);
		new JavaMethodAnnotationVisitor().visit(cu, this.models);
	}

	private void generateUnitBuilder(AnnotationModel model, String targetPath, String targetPackage) {
		BuilderMethodFactory bmf = new BuilderMethodFactory(model, targetPackage);
		String builderClassIdentifier = model.getIdentifier() + "UnitBuilder";

		List<MethodSpec> defaultMethods = getBuilderMethodTypes(model.getDefaultCodeUnit().getType())
				.stream()
				.map(bmf::createForType)
				.collect(Collectors.toList());

		List<MethodSpec> variabilityMethods = model.getVariablityAnnotations()
				.stream()
				.map(anno -> bmf
						.createForType(BuilderMethodMapper.getBuilderMethodType(anno)))
				.collect(Collectors.toList());

		TypeSpec builderType = TypeSpec
				.classBuilder(builderClassIdentifier)
				.addModifiers(Modifier.PUBLIC)
				.addField(CodeUnit.class, "codeUnit", Modifier.PRIVATE)
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

	private List<BuilderMethodType> getBuilderMethodTypes(CodeUnitType builderType) {
		List<BuilderMethodType> methods;

		switch(builderType) {
			case CLASS:
				methods = getClassBuilderMethodTypes();
				break;
			case METHOD:
				methods = getMethodBuilderMethodTypes();
				break;
			case FIELD:
			default:
				methods = getFieldBuilderMethodTypes();
				break;
		}

		return methods;
	}

	private List<BuilderMethodType> getClassBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.CLASSBUILDER_END
		);
	}

	private List<BuilderMethodType> getMethodBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.END,
				BuilderMethodType.WITH_METHOD_BODY,
				BuilderMethodType.WITH_RETURN_TYPE
		);
	}

	private List<BuilderMethodType> getFieldBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.END
		);
	}
}
