/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 28.01.18 12:43
 */

package scarlet.model;

public class JavaConstructorBody {
	private String content;

	public JavaConstructorBody() {
		this.content = "";
	}

	public void setContentFromString(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}
}
