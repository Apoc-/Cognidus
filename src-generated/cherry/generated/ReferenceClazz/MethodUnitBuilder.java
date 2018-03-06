package cherry.generated.ReferenceClazz;

import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitModifier;
import cherry.model.MethodCodeUnit;
import cherry.platform.CodeUnitVariablityChecker;
import cherry.platform.DefaultCodeUnitProvider;
import java.lang.String;
import org.apache.commons.lang3.SerializationUtils;

public class MethodUnitBuilder {
  private MethodCodeUnit codeUnit;

  private MethodUnitBuilder() {
    initializeDefaultCodeUnit();
  }

  private void initializeDefaultCodeUnit() {
    // Initializes this builder's data with default data encoded into a byte[]
    byte[] serializedCodeUnit = new byte[] {-84, -19, 0, 5, 115, 114, 0, 27, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 77, 101, 116, 104, 111, 100, 67, 111, 100, 101, 85, 110, 105, 116, 79, 97, -23, 55, -46, -97, -78, -33, 2, 0, 0, 120, 114, 0, 21, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, -47, -4, 97, 55, -68, -1, -114, -106, 2, 0, 4, 76, 0, 4, 100, 97, 116, 97, 116, 0, 15, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 77, 97, 112, 59, 76, 0, 6, 112, 97, 114, 101, 110, 116, 116, 0, 23, 76, 99, 104, 101, 114, 114, 121, 47, 109, 111, 100, 101, 108, 47, 67, 111, 100, 101, 85, 110, 105, 116, 59, 76, 0, 12, 115, 117, 98, 67, 111, 100, 101, 85, 110, 105, 116, 115, 116, 0, 16, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 59, 76, 0, 4, 116, 121, 112, 101, 116, 0, 27, 76, 99, 104, 101, 114, 114, 121, 47, 109, 111, 100, 101, 108, 47, 67, 111, 100, 101, 85, 110, 105, 116, 84, 121, 112, 101, 59, 120, 112, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 72, 97, 115, 104, 77, 97, 112, 5, 7, -38, -63, -61, 22, 96, -47, 3, 0, 2, 70, 0, 10, 108, 111, 97, 100, 70, 97, 99, 116, 111, 114, 73, 0, 9, 116, 104, 114, 101, 115, 104, 111, 108, 100, 120, 112, 63, 64, 0, 0, 0, 0, 0, 0, 119, 8, 0, 0, 0, 16, 0, 0, 0, 0, 120, 112, 115, 114, 0, 19, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 65, 114, 114, 97, 121, 76, 105, 115, 116, 120, -127, -46, 29, -103, -57, 97, -99, 3, 0, 1, 73, 0, 4, 115, 105, 122, 101, 120, 112, 0, 0, 0, 0, 119, 4, 0, 0, 0, 0, 120, 126, 114, 0, 25, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, 84, 121, 112, 101, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 114, 0, 14, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 69, 110, 117, 109, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 112, 116, 0, 6, 77, 69, 84, 72, 79, 68};
    this.codeUnit = SerializationUtils.deserialize(serializedCodeUnit);
  }

  public static MethodUnitBuilder createWithIdentifier(String identifier) {
    MethodUnitBuilder cub = new MethodUnitBuilder();
    cub.codeUnit.addCodeUnitDatum(CodeUnitDatumType.IDENTIFIER, identifier);
    return cub;
  }

  public MethodCodeUnit end() {
    new CodeUnitVariablityChecker(this.codeUnit).checkVariabilities();
    return codeUnit;
  }

  public MethodUnitBuilder withMethodBody(String code) {
    this.codeUnit.addSubCodeUnit(DefaultCodeUnitProvider.createMethodBodyCodeUnit(code));
    return this;
  }

  public MethodUnitBuilder withReturnType(String typeName) {
    this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.RETURN_TYPE, typeName);
    return this;
  }

  public MethodUnitBuilder withModifiers(CodeUnitModifier... modifiers) {
    this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.MODIFIER, modifiers);
    return this;
  }

  public MethodUnitBuilder withParameter(String identifier, String typeName) {
    this.codeUnit.addSubCodeUnit(DefaultCodeUnitProvider.createMethodParameterCodeUnit(identifier, typeName));
    return this;
  }
}
