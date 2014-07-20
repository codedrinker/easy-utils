package org.easyutils.io;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.easyutils.convert.StringUtility;

public class FileUtility {

	/**
	 * 
	 * @param directory
	 *            �ļ�Ŀ¼
	 * @param fix
	 *            �޸����ֵ�ǰһ����
	 * @param nix
	 *            �޸����ֵĺ�һ����
	 */
	public void renameFiles(String directory, String fix, String nix) {
		StringUtility utility = new StringUtility();
		File f = new File(directory);
		File files[] = f.listFiles();
		for (File file : files) {
			String filename = file.getName();
			int start = filename.indexOf(fix);
			int end = filename.indexOf(nix);
			String rep = filename.substring(start + 1, end);
			file.renameTo(new File(directory + File.separator
					+ filename.replaceAll(rep, utility.convertToNum(rep))));
		}
	}

	private static Properties cache = new Properties();

	/**
	 * ��ȡָ��key��ֵ
	 * 
	 * @param key
	 *            String properties�ļ���ָ���ļ�ֵ����
	 * @param filename
	 *            String properties�ļ�������
	 * @return String ��ö�Ӧ�ļ�ֵ���ַ���
	 */
	public static String getValue(String key, String filename) {
		try {
			cache.load(FileUtility.class.getClassLoader().getResourceAsStream(
					filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cache.getProperty(key);
	}

	/**
	 * �����������·��
	 * 
	 * @param dirs
	 *            String [] ����һ��String���͵����飬�ӵ�һ�������һ��Ϊ���ļ���classpath��Ŀ¼���µ��ļ�Ŀ¼�� ���һ��ζ�ļ������֣������չ��
	 * @return String ���ؾ����ͼƬ��λ�ã���Ҫ�ǽ���ļ���File.separator����
	 */
	public static String getRelativeFileName(String[] dirs) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(System.getProperty("user.dir"));
		if (dirs != null && dirs.length > 0) {
			for (String dir : dirs) {
				fileName.append(File.separator + dir);
			}
		}
		return fileName.toString();
	}

	/**
	 * ����ļ������·��
	 * 
	 * @param dirs
	 *            String ... ����һ��String���͵Ŀɱ�������ӵ�һ�������һ��Ϊ���ļ���classpath��Ŀ¼���µ��ļ�Ŀ¼�� ���һ��ζ�ļ������֣������չ��
	 * @return String ���ؾ����ͼƬ��λ�ã���Ҫ�ǽ���ļ���File.separator����
	 */
	public static String getRelativeFilePath(String... dirs) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(System.getProperty("user.dir"));
		if (dirs != null && dirs.length > 0) {
			for (String dir : dirs) {
				fileName.append(File.separator + dir);
			}
		}

		return fileName.toString();
	}

	/**
	 * ����ļ��ľ���·��
	 * 
	 * @param dirs
	 *            String [] ����һ��String���͵����飬�ӵ�һ�������һ��Ϊ���ļ���classpath��Ŀ¼���µ��ļ�Ŀ¼�� ���һ��ζ�ļ������֣������չ��
	 * @return String ���ؾ����ͼƬ��λ�ã���Ҫ�ǽ���ļ���File.separator����
	 */
	public static String getAbsoluteFileName(String[] dirs) {
		StringBuffer fileName = new StringBuffer();
		if (dirs != null && dirs.length > 0) {
			for (String dir : dirs) {
				fileName.append(dir + File.separator);
			}

		}
		int length = fileName.length();
		return fileName.substring(0, length - 1);
	}

	/**
	 * ����ļ��ľ���·��
	 * 
	 * @param dirs
	 *            String ... ����һ��String���͵Ŀɱ�������ӵ�һ�������һ��Ϊ���ļ���classpath��Ŀ¼���µ��ļ�Ŀ¼�� ���һ��ζ�ļ������֣������չ��
	 * @return String ���ؾ����ͼƬ��λ�ã���Ҫ�ǽ���ļ���File.separator����
	 */
	public static String getAbsoluteFilePath(String... dirs) {
		StringBuffer fileName = new StringBuffer();
		if (dirs != null && dirs.length > 0) {
			for (String dir : dirs) {
				fileName.append(dir + File.separator);
			}

		}
		int length = fileName.length();
		return fileName.substring(0, length - 1);
	}

}
