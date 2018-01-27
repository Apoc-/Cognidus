/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 21:00
 */

package amber.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface VariableModifier {
}
