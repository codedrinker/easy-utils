package org.easyutils.exec;

import java.io.IOException;

public class ExecUtility {

	/**
	 * 打开CHM类型的文档文件，但只是用于Windows系统
	 * 
	 * @param filename
	 *            String 类型的文件的绝对或者相对路径
	 */
	public static void openCHM(String filename) {
		try {
			Runtime.getRuntime().exec("hh " + "\"" + filename + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
