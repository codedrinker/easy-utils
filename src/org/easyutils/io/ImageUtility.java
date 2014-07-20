package org.easyutils.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtility extends FileUtility {

	/**
	 * 
	 * @param path
	 *            String 传入可操作的图片的绝对路径
	 * @return BufferedImage 反应具有缓冲的Image对象
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
	 *            String 可变参数 用于直接传递进来的目录结构获得可操作的图片路径
	 * @return BufferedImage 反应具有缓冲的Image对象
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
