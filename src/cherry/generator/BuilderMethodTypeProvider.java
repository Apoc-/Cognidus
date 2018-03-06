/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 19.02.18 19:25
 */

package cherry.generator;

import cherry.model.CodeUnitType;

import java.util.List;

public class BuilderMethodTypeProvider {
	public static List<BuilderMethodType> getMethodTypeList(CodeUnitType codeUnitType) {
			List<BuilderMethodType> methods;

			switch(codeUnitType) {
				case CLASS:
					methods = getClassBuilderMethodTypes();
					break;
				case METHOD:
					methods = getMethodBuilderMethodTypes();
					break;
				case CONSTRUCTOR:
					methods = getConstructorBuilderMethodTypes();
					break;
				case FIELD:
				default:
					methods = getFieldBuilderMethodTypes();
					break;
			}

			return methods;
	}

	private static List<BuilderMethodType> getFieldBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.END
		);
	}

	private static List<BuilderMethodType> getClassBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.CLASSBUILDER_END,
				BuilderMethodType.WITH_FIELD,
				BuilderMethodType.WITH_METHOD,
				BuilderMethodType.WITH_CONSTRUCTOR
		);
	}

	private static List<BuilderMethodType> getMethodBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE_WITH_IDENTIFIER,
				BuilderMethodType.END,
				BuilderMethodType.WITH_METHOD_BODY,
				BuilderMethodType.WITH_RETURN_TYPE
		);
	}

	private static List<BuilderMethodType> getConstructorBuilderMethodTypes() {
		return List.of(
				BuilderMethodType.CONSTRUCTOR,
				BuilderMethodType.INIT_DEF_CODE_UNIT,
				BuilderMethodType.CREATE,
				BuilderMethodType.END,
				BuilderMethodType.WITH_METHOD_BODY
		);
	}
}