/*
 * Copyright (c) Apoc- 2017
 *
 * File last modfied: 26.12.17 00:07
 */

package cherry.model;

import java.io.Serializable;
import java.util.Arrays;

public class CodeUnitDatum<T> implements Serializable {
	private final T datumData;

	public CodeUnitDatum(T datumData) {
		this.datumData = datumData;
	}

	public T getDatumData() {
		return datumData;
	}

	@Override
	public String toString() {
		if(datumData.getClass().isArray())
			return Arrays.toString((Object[]) datumData);

		return datumData.toString();
	}
}
