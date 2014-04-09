package cn.hicc.net.frame;

import org.easyutils.swing.FrameUtility;
import org.junit.Test;

public class FrameUtilityTest {
	@Test
	public void showScreenSize() {
		System.out.println(FrameUtility.getScreenSize().width + "*"
				+ FrameUtility.getScreenSize().height);
	}
}
