package cherry.generated;

import cherry.model.CodeUnitDatumType;
import cherry.model.CodeUnitModifier;
import cherry.model.MethodCodeUnit;
import cherry.platform.CodeUnitBuilderUtils;
import java.lang.Class;
import java.lang.String;
import org.apache.commons.lang3.SerializationUtils;

public class MethodModUnitBuilder {
  private MethodCodeUnit codeUnit;

  private MethodModUnitBuilder() {
    initializeDefaultCodeUnit();
  }

  private void initializeDefaultCodeUnit() {
    // Initializes this builder's data with default data encoded into a byte[]
    byte[] serializedCodeUnit = new byte[] {-84, -19, 0, 5, 115, 114, 0, 27, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 77, 101, 116, 104, 111, 100, 67, 111, 100, 101, 85, 110, 105, 116, 79, 97, -23, 55, -46, -97, -78, -33, 2, 0, 0, 120, 114, 0, 21, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, -4, 20, 31, 95, -80, 39, -16, 90, 2, 0, 3, 76, 0, 4, 100, 97, 116, 97, 116, 0, 15, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 77, 97, 112, 59, 76, 0, 12, 115, 117, 98, 67, 111, 100, 101, 85, 110, 105, 116, 115, 116, 0, 16, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 59, 76, 0, 4, 116, 121, 112, 101, 116, 0, 27, 76, 99, 104, 101, 114, 114, 121, 47, 109, 111, 100, 101, 108, 47, 67, 111, 100, 101, 85, 110, 105, 116, 84, 121, 112, 101, 59, 120, 112, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 72, 97, 115, 104, 77, 97, 112, 5, 7, -38, -63, -61, 22, 96, -47, 3, 0, 2, 70, 0, 10, 108, 111, 97, 100, 70, 97, 99, 116, 111, 114, 73, 0, 9, 116, 104, 114, 101, 115, 104, 111, 108, 100, 120, 112, 63, 64, 0, 0, 0, 0, 0, 0, 119, 8, 0, 0, 0, 16, 0, 0, 0, 0, 120, 115, 114, 0, 19, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 65, 114, 114, 97, 121, 76, 105, 115, 116, 120, -127, -46, 29, -103, -57, 97, -99, 3, 0, 1, 73, 0, 4, 115, 105, 122, 101, 120, 112, 0, 0, 0, 1, 119, 4, 0, 0, 0, 1, 115, 113, 0, 126, 0, 1, 115, 113, 0, 126, 0, 6, 63, 64, 0, 0, 0, 0, 0, 12, 119, 8, 0, 0, 0, 16, 0, 0, 0, 2, 126, 114, 0, 30, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, 68, 97, 116, 117, 109, 84, 121, 112, 101, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 114, 0, 14, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 69, 110, 117, 109, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 112, 116, 0, 10, 73, 68, 69, 78, 84, 73, 70, 73, 69, 82, 115, 114, 0, 26, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, 68, 97, 116, 117, 109, 97, 71, -38, 17, -62, -37, -11, 8, 2, 0, 1, 76, 0, 9, 100, 97, 116, 117, 109, 68, 97, 116, 97, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 120, 112, 116, 0, 1, 110, 126, 113, 0, 126, 0, 12, 116, 0, 9, 68, 65, 84, 65, 95, 84, 89, 80, 69, 115, 113, 0, 126, 0, 16, 118, 114, 0, 3, 105, 110, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 112, 120, 115, 113, 0, 126, 0, 8, 0, 0, 0, 0, 119, 4, 0, 0, 0, 0, 120, 126, 114, 0, 25, 99, 104, 101, 114, 114, 121, 46, 109, 111, 100, 101, 108, 46, 67, 111, 100, 101, 85, 110, 105, 116, 84, 121, 112, 101, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 113, 0, 126, 0, 13, 116, 0, 12, 77, 69, 84, 72, 79, 68, 95, 80, 65, 82, 65, 77, 120, 126, 113, 0, 126, 0, 26, 116, 0, 6, 77, 69, 84, 72, 79, 68};
    this.codeUnit = SerializationUtils.deserialize(serializedCodeUnit);
  }

  public static MethodModUnitBuilder createWithIdentifier(String identifier) {
    MethodModUnitBuilder cub = new MethodModUnitBuilder();
    cub.codeUnit.addCodeUnitDatum(CodeUnitDatumType.IDENTIFIER, identifier);
    return cub;
  }

  public MethodCodeUnit end() {
    return codeUnit;
  }

  public MethodModUnitBuilder withMethodBody(String code) {
    this.codeUnit.addSubCodeUnit(CodeUnitBuilderUtils.createMethodBodyCodeUnit(code));
    return this;
  }

  public MethodModUnitBuilder withReturnType(Class dataType) {
    this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.RETURN_TYPE, dataType);
    return this;
  }

  public MethodModUnitBuilder withModifiers(CodeUnitModifier... modifiers) {
    this.codeUnit.addCodeUnitDatum(CodeUnitDatumType.MODIFIER, modifiers);
    return this;
  }
}
