/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 05.01.18 21:01
 */

package amber.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//todo what about self references? eg instance for singleton
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface FixedCodeUnit {
}
