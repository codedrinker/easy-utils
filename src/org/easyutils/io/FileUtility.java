package org.easyutils.io;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.easyutils.convert.StringUtility;

public class FileUtility {

	/**
	 * 
	 * @param directory
	 *            文件目录
	 * @param fix
	 *            修改名字的前一个字
	 * @param nix
	 *            修改名字的后一个字
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
	 * 获取指定key的值
	 * 
	 * @param key
	 *            String properties文件中指定的键值名称
	 * @param filename
	 *            String properties文件的名称
	 * @return String 获得对应的键值的字符串
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
	 * 获得问题的相对路径
	 * 
	 * @param dirs
	 *            String [] 传递一个String类型的数组，从第一个到最后一个为从文件的classpath的目录向下的文件目录， 最后一个味文件的名字，外加扩展名
	 * @return String 返回具体的图片的位置，主要是解决文件的File.separator问题
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
	 * 获得文件的相对路径
	 * 
	 * @param dirs
	 *            String ... 传递一个String类型的可变参数，从第一个到最后一个为从文件的classpath的目录向下的文件目录， 最后一个味文件的名字，外加扩展名
	 * @return String 返回具体的图片的位置，主要是解决文件的File.separator问题
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
	 * 获得文件的绝对路径
	 * 
	 * @param dirs
	 *            String [] 传递一个String类型的数组，从第一个到最后一个为从文件的classpath的目录向下的文件目录， 最后一个味文件的名字，外加扩展名
	 * @return String 返回具体的图片的位置，主要是解决文件的File.separator问题
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
	 * 获得文件的绝对路径
	 * 
	 * @param dirs
	 *            String ... 传递一个String类型的可变参数，从第一个到最后一个为从文件的classpath的目录向下的文件目录， 最后一个味文件的名字，外加扩展名
	 * @return String 返回具体的图片的位置，主要是解决文件的File.separator问题
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
