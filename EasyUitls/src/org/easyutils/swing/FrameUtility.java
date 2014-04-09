package org.easyutils.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtility {
	/**
	 * 控制Frame让其居中显示,但是尽量在setVisiable之前使用
	 * 
	 * @param frame
	 */
	public static void center(JFrame frame) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		int x = (int) screenSize.getWidth() / 2 - (int) frameSize.getWidth()
				/ 2;
		int y = (int) screenSize.getHeight() / 2 - (int) frameSize.getHeight()
				/ 2;
		frame.setLocation(x, y);
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
