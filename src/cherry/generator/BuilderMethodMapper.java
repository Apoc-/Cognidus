/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 22:51
 */

package cherry.generator;

import amber.model.AnnotationDatum;

import java.util.Map;

public class BuilderMethodMapper {
	private static Map<AnnotationDatum, BuilderMethodType> map;
	static {
		map = Map.of(
				AnnotationDatum.VARIABLE_MODIFIERS, BuilderMethodType.WITH_MODIFIERS,
				AnnotationDatum.VARIABLE_DATATYPE, BuilderMethodType.WITH_DATA_TYPE,
				AnnotationDatum.CAN_HAVE_SUBCODEUNITS, BuilderMethodType.WITH_SUB_CODEUNIT);
	}

	public static BuilderMethodType getBuilderMethodType(AnnotationDatum annotationDatum) {
		return map.get(annotationDatum);
	}
}
