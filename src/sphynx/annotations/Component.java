/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 25.12.17 23:25
 */

package sphynx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Component {
	String identifier();
}