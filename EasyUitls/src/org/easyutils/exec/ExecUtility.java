package org.easyutils.exec;

import java.io.IOException;

public class ExecUtility {

	/**
	 * ��CHM���͵��ĵ��ļ�����ֻ������Windowsϵͳ
	 * 
	 * @param filename
	 *            String ���͵��ļ��ľ��Ի������·��
	 */
	public static void openCHM(String filename) {
		try {
			Runtime.getRuntime().exec("hh " + "\"" + filename + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
