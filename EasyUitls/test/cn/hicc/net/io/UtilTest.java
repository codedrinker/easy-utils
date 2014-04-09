package cn.hicc.net.io;

import org.apache.log4j.Logger;
import org.easyutils.io.FileUtility;

public class UtilTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UtilTest.class);

	public static void main(String[] args) {
		String p1_MerId = FileUtility.getValue("p1_MerId",
				"merchantInfo.properties");
		System.out.println(p1_MerId);
		logger.info(p1_MerId);
		System.out
				.println(FileUtility.getAbsoluteFilePath("d:", "23", "a.jpg"));
	}
}
