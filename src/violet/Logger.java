/*
 * Copyright (c) Apoc- 2018
 *
 * File last modfied: 06.02.18 13:21
 */

package violet;

import java.io.*;

public class Logger {
	private OutputStream targetStream;

	public Logger(OutputStream targetStream) {
		this.targetStream = targetStream;
	}

	public static Logger console() {
		return new Logger(System.out);
	}

	public void logInfo(String s) {
		String prefix = "[info] ";
		logMessage(prefix + s);
	}

	public void logError(String s) {
		String prefix = "[error] ";
		logMessage(prefix + s);
	}

	//todo: implement solution for filestreams
	private void logMessage(String msg) {
		PrintStream ps = new PrintStream(targetStream);
		ps.println(msg);
	}
}
