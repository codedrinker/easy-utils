package org.easyutils.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtility extends FileUtility {

	/**
	 * 
	 * @param path
	 *            String ����ɲ�����ͼƬ�ľ���·��
	 * @return BufferedImage ��Ӧ���л����Image����
	 */
	public static BufferedImage getImage(String path) {
		try {
			File input = new File(path);
			BufferedImage bi = ImageIO.read(input);
			return bi;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param dirs
	 *            String �ɱ���� ����ֱ�Ӵ��ݽ�����Ŀ¼�ṹ��ÿɲ�����ͼƬ·��
	 * @return BufferedImage ��Ӧ���л����Image����
	 */
	public static BufferedImage getImage(String... dirs) {
		try {
			StringBuffer fileName = new StringBuffer();
			fileName.append(System.getProperty("user.dir"));
			if (dirs != null && dirs.length > 0) {
				for (String dir : dirs) {
					fileName.append(File.separator + dir);
				}
			}

			File file = new File(fileName.toString());
			BufferedImage bi = ImageIO.read(file);
			return bi;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
