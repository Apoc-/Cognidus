/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.03.18 18:23
 */

package cherry.platform;

import cherry.model.CodeUnit;
import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitType;
import violet.Logger;

public class CodeUnitVariablityChecker {
	private CodeUnit codeUnit;

	public CodeUnitVariablityChecker(CodeUnit codeUnit) {
		this.codeUnit = codeUnit;
	}

	public void checkVariabilities() {
		switch(codeUnit.getType()) {
			case CLASS:
				break;
			case FIELD:
				checkFieldVariabilities();
				break;
			case CONSTRUCTOR:
				break;
			case METHOD:
				break;
			case METHOD_BODY:
				break;
			case METHOD_PARAM:
				break;
		}
	}

	private void checkFieldVariabilities() {
		if(!codeUnit.hasDatum(CodeUnitDatumType.DATA_TYPE)) {
			String identifier = (String) codeUnit.getCodeUnitDatum(CodeUnitDatumType.IDENTIFIER).getDatumData();

			Logger.console().logError("CodeUnit for " + identifier + " had no data type set, setting default datatype to Object");
			codeUnit.addCodeUnitDatum(CodeUnitDatumType.DATA_TYPE, "Object");
		}
	}


}
