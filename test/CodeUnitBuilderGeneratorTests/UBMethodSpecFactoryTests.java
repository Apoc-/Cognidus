/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 19:47
 */

package CodeUnitBuilderGeneratorTests;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import sphynx.ubgenerator.UBCodeUnitBuilder;
import sphynx.ubgenerator.UBMethodSpec;
import sphynx.ubgenerator.UBMethodSpecFactory;

public class UBMethodSpecFactoryTests {
	@org.junit.jupiter.api.Test
	void CreateWithIdentifierMethodSpecFactoryTest() {
		TypeName type = ClassName.get(UBCodeUnitBuilder.class);
		MethodSpec ms = UBMethodSpecFactory.createForType(UBMethodSpec.CREATE_WITH_IDENTIFIER, type);
		System.out.println(ms.toString());
	}

	@org.junit.jupiter.api.Test
	void WithModifiersMethodSpecFactoryTest() {
		TypeName type = ClassName.get(UBCodeUnitBuilder.class);
		MethodSpec ms = UBMethodSpecFactory.createForType(UBMethodSpec.WITH_MODIFIERS, type);
		System.out.println(ms.toString());
	}

	@org.junit.jupiter.api.Test
	void WithDataTypeMethodSpecFactoryTest() {
		TypeName type = ClassName.get(UBCodeUnitBuilder.class);
		MethodSpec ms = UBMethodSpecFactory.createForType(UBMethodSpec.WITH_DATA_TYPE, type);
		System.out.println(ms.toString());
	}

	@org.junit.jupiter.api.Test
	void WithSubCodeUnitMethodSpecFactoryTest() {
		TypeName type = ClassName.get(UBCodeUnitBuilder.class);
		MethodSpec ms = UBMethodSpecFactory.createForType(UBMethodSpec.WITH_SUB_CODEUNIT, type);
		System.out.println(ms.toString());
	}

	@org.junit.jupiter.api.Test
	void EndMethodSpecFactoryTest() {
		TypeName type = ClassName.get(UBCodeUnitBuilder.class);
		MethodSpec ms = UBMethodSpecFactory.createForType(UBMethodSpec.END, type);
		System.out.println(ms.toString());
	}
}
