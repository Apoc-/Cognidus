/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:51
 */

package cherry.generator;

import amber.model.AnnotationType;

import java.util.Map;

public class BuilderMethodMapper {
	private BuilderMethodMapper() {
	}

	private static Map<AnnotationType, BuilderMethodType> map;
	static {
		map = Map.of(
				AnnotationType.VARIABLE_MODIFIERS, BuilderMethodType.WITH_MODIFIERS,
				AnnotationType.VARIABLE_DATATYPE, BuilderMethodType.WITH_DATA_TYPE,
				AnnotationType.CAN_HAVE_SUBCODEUNITS, BuilderMethodType.WITH_SUB_CODEUNIT);
	}

	public static BuilderMethodType getBuilderMethodType(AnnotationType annotationType) {
		return map.get(annotationType);
	}
}