/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 19:44
 */

package cherry.generator;

import amber.model.AnnotationModel;
import amber.model.AnnotationType;
import cherry.model.*;
import cherry.platform.DefaultCodeUnitProvider;
import cherry.platform.CodeUnitReferenceResolver;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.SerializationUtils;

import javax.lang.model.element.Modifier;
import java.util.Arrays;

public class BuilderMethodFactory {
	private String builderClassIdentifier;
	private String packageIdentifier;
	private AnnotationModel annotationModel;

	BuilderMethodFactory(AnnotationModel annotationModel, String targetPackage) {
		this.annotationModel = annotationModel;
		this.builderClassIdentifier = annotationModel.getIdentifier() + "UnitBuilder";
		this.packageIdentifier = targetPackage;
	}

	public MethodSpec createForType(BuilderMethodType builderMethodType, Class codeUnitType) {
		MethodSpec createdMethodSpec = null;
		TypeName builderTypeName = ClassName.get(packageIdentifier, builderClassIdentifier);

		switch(builderMethodType) {
			case CONSTRUCTOR:
				createdMethodSpec = createConstructor();
				break;
			case WITH_FIELD:
				createdMethodSpec = createWithFieldSpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_METHOD:
				createdMethodSpec = createWithMethodSpec(builderMethodType.toString(), builderTypeName);
				break;
			case CREATE_WITH_IDENTIFIER:
				createdMethodSpec = createCreateWithIdSpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_DATA_TYPE:
				createdMethodSpec = createWithDataTypeSpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_MODIFIERS:
				createdMethodSpec = createWithModsSpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_SUB_CODEUNIT:
				createdMethodSpec = createWithSubCodeUnitSpec(builderMethodType.toString(), builderTypeName);
				break;
			case INIT_DEF_CODE_UNIT:
				createdMethodSpec = createInitDefCodeUnitMethod();
				break;
			case CLASSBUILDER_END:
				createdMethodSpec = createClassBuilderEndSpec(builderMethodType.toString(), codeUnitType);
				break;
			case END:
				createdMethodSpec = createEndSpec(builderMethodType.toString(), codeUnitType);
				break;
			case WITH_PARAMETER:
				createdMethodSpec = createAddParameterSpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_METHOD_BODY:
				createdMethodSpec = createWithBodySpec(builderMethodType.toString(), builderTypeName);
				break;
			case WITH_RETURN_TYPE:
				createdMethodSpec = createWithReturnType(builderMethodType.toString(), builderTypeName);
		}

		return createdMethodSpec;
	}

	private MethodSpec createConstructor() {
		return MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addStatement("initializeDefaultCodeUnit()")
				.build();
	}

	private MethodSpec createAddParameterSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier).addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(String.class, "identifier")
				.addParameter(String.class, "typeName")
				.addStatement("this.codeUnit.addSubCodeUnit($T.createMethodParameterCodeUnit(identifier, typeName))", DefaultCodeUnitProvider.class)
				.addStatement("return this")
				.build();
	}

	private MethodSpec createCreateWithIdSpec(String identifier, TypeName builderType) {
		MethodSpec.Builder msb = MethodSpec.methodBuilder(identifier);

		msb.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(builderType)
				.addParameter(String.class, "identifier")
				.addStatement("$T cub = new $T()", builderType, builderType)
				.addStatement("cub.codeUnit.addCodeUnitDatum($T.$L, identifier)", CodeUnitDatumType.class, "IDENTIFIER");

		if(annotationModel.getExtensionAnnotations().contains(AnnotationType.HAS_GETTER)) {
			msb.addStatement("cub.codeUnit.addCodeUnitDatum($T.$L, true)", CodeUnitDatumType.class, "GETTER");
		}

		if(annotationModel.getExtensionAnnotations().contains(AnnotationType.HAS_SETTER)) {
			msb.addStatement("cub.codeUnit.addCodeUnitDatum($T.$L, true)", CodeUnitDatumType.class, "SETTER");
		}

		return msb
				.addStatement("return cub")
				.build();
	}

	private MethodSpec createWithModsSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(ArrayTypeName.get(CodeUnitModifier[].class), "modifiers")
				.varargs()
				.addStatement("this.codeUnit.addCodeUnitDatum($T.$L, modifiers)", CodeUnitDatumType.class, "MODIFIER")
				.addStatement("return this")
				.build();
	}

	private MethodSpec createWithDataTypeSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(String.class, "typeName")
				.addStatement("this.codeUnit.addCodeUnitDatum($T.$L, typeName)", CodeUnitDatumType.class, "DATA_TYPE")
				.addStatement("return this")
				.build();
	}

	//todo del
	private MethodSpec createWithSubCodeUnitSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(CodeUnit.class, "codeUnit")
				.addStatement("this.codeUnit.addSubCodeUnit(codeUnit)")
				.addStatement("return this")
				.build();
	}

	private MethodSpec createWithFieldSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(FieldCodeUnit.class, "codeUnit")
				.addStatement("this.codeUnit.addSubCodeUnit(codeUnit)")
				.addStatement("return this")
				.build();
	}

	private MethodSpec createWithMethodSpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(MethodCodeUnit.class, "codeUnit")
				.addStatement("this.codeUnit.addSubCodeUnit(codeUnit)")
				.addStatement("return this")
				.build();
	}

	private MethodSpec createClassBuilderEndSpec(String identifier, Class codeUnitType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(codeUnitType)
				.addStatement("this.codeUnit.addSubCodeUnits($T.createDefaultMethodCodeUnits(codeUnit))", DefaultCodeUnitProvider.class)
				.addStatement("new $T(this.codeUnit).resolveReferences()", CodeUnitReferenceResolver.class)
				.addStatement("return codeUnit")
				.build();
	}

	private MethodSpec createEndSpec(String identifier, Class codeUnitType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(codeUnitType)
				.addStatement("return codeUnit")
				.build();
	}

	private MethodSpec createWithBodySpec(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier).addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(String.class, "code")
				.addStatement("this.codeUnit.addSubCodeUnit($T.createMethodBodyCodeUnit(code))", DefaultCodeUnitProvider.class)
				.addStatement("return this")
				.build();
	}

	private MethodSpec createWithReturnType(String identifier, TypeName builderType) {
		return MethodSpec.methodBuilder(identifier)
				.addModifiers(Modifier.PUBLIC)
				.returns(builderType)
				.addParameter(String.class, "typeName")
				.addStatement("this.codeUnit.addCodeUnitDatum($T.$L, typeName)", CodeUnitDatumType.class, "RETURN_TYPE")
				.addStatement("return this")
				.build();
	}

	//Todo: Talk why THIS should be the solution
	private MethodSpec createInitDefCodeUnitMethod() {
		CodeUnit sourceCodeUnit = annotationModel.getDefaultCodeUnit();

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
