/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 28.01.18 11:24
 */

package scarlet.model;

public class JavaMethodBody {
	private String content;

	public JavaMethodBody() {
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
