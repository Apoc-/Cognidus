/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 11.01.18 23:18
 */

package amber.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//todo what about static fields?
@Target({ElementType.FIELD})
public @interface HasGetter {
}
