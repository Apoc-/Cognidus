/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 14.02.18 15:09
 */

package cherry.platform;

import cherry.model.ClassCodeUnit;
import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitType;

public class CodeUnitReferenceResolver {
	private ClassCodeUnit classCodeUnit;
	private String classIdentifier;

	public CodeUnitReferenceResolver(ClassCodeUnit codeUnit) {
		this.classCodeUnit = codeUnit;
		this.classIdentifier = getIdentifier();
	}

	public void resolveReferences() {
		classCodeUnit.getSubCodeUnits().forEach(this::resolveClassReferences);
	}

	//resolves all self references from the reference implementation to the actual class identifier
	private void resolveClassReferences(CodeUnit codeUnit) {
		if(!codeUnit.hasDatum(CodeUnitDatumType.PARENT_CLASS_REF)) {
			return; //early-out, nothing to resolve here
		}

		switch(codeUnit.getType()) {
			case METHOD_BODY:
				resolveMethodBodyReference(codeUnit);
				break;
			case METHOD_PARAM:
			case FIELD:
				resolveDataTypeReference(codeUnit);
				break;
			case METHOD:
				resolveReturnTypeReference(codeUnit);
				break;
			default:
				break;
		}

		codeUnit.getSubCodeUnits().forEach(this::resolveClassReferences);
	}

	private void resolveDataTypeReference(CodeUnit codeUnit) {
		codeUnit.addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, this.classIdentifier);
	}

	private void resolveReturnTypeReference(CodeUnit codeUnit) {
		codeUnit.addCodeUnitDatum(CodeUnitDatumType.RETURN_TYPE, this.classIdentifier);
	}

	private void resolveMethodBodyReference(CodeUnit codeUnit) {
		String codeBody = getMethodBody(codeUnit);
		String refIdentifier = getRefIdentifier(codeUnit);

		String pattern = "\\b" + refIdentifier + "\\b";
		String resolvedBody = codeBody.replaceAll(pattern, this.classIdentifier);

		codeUnit.addCodeUnitDatum(CodeUnitDatumType.METHOD_BODY_STRING, resolvedBody);
	}

	private String getRefIdentifier(CodeUnit unit) {
		return (String) unit.getCodeUnitDatum(CodeUnitDatumType.PARENT_CLASS_REF).getDatumData();
	}

	private String getIdentifier() {
		return (String) classCodeUnit.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();
	}

	private String getMethodBody(CodeUnit codeUnit) {
		return (String) codeUnit.getCodeUnitDatum(CodeUnitDatumType.METHOD_BODY_STRING).getDatumData();
	}
}
