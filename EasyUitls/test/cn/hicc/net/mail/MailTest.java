package cn.hicc.net.mail;

import java.util.HashMap;
import java.util.Map;

import org.easyutils.io.FileUtility;
import org.easyutils.mail.Translater;
import org.junit.Test;

public class MailTest {

	@Test
	public void sendStandard() {
		String host = "smtp.yeah.net";
		String username = "taohongbei@yeah.net";
		String password = "cxrhthb";
		String addresslist = "newangchunlei@163.com,swangchunlei@sina.com";
		String content = "����ǵ�һ���򵥵Ĳ���";
		String mimeType = "text/html;charset=GBK";
		String subject = "����ǵ�һ���򵥵Ĳ���";
		Map<String, String> from = new HashMap<String, String>();
		from.put("�Ժ決��", "taohongbei@yeah.net");
		Translater.send(host, username, password, addresslist, true, content,
				mimeType, subject, from);

	}

	@Test
	public void sendStandardAttchs() {
		boolean debug = true;
		String host = "smtp.yeah.net";
		String username = "taohongbei@yeah.net";
		String password = "cxrhthb";
		String addresslist = "newangchunlei@163.com,swangchunlei@sina.com";
		String content = "����ǵڶ����򵥵Ĳ���";
		String mimeType = "text/html;charset=GBK";
		String subject = "����ǵڶ����򵥵Ĳ���";
		Map<String, String> from = new HashMap<String, String>();
		from.put("�Ժ決��", "taohongbei@yeah.net");
		Map<String, String> attchs = new HashMap<String, String>();
		attchs.put(
				"ͼƬ.jpg",
				FileUtility.getRelativeFileName(new String[] { "attchs",
						"404.jpg" }));
		attchs.put("�ļ�.txt",
				FileUtility.getRelativeFilePath("attchs", "attch.txt"));
		Translater.send(debug, host, username, password, subject, addresslist,
				content, mimeType, from, attchs);
	}

	@Test
	public void sendStandardIO() {
		boolean debug = true;
		String addresslist = "swangchunlei@sina.com";
		String desPath = FileUtility.getRelativeFilePath("des",
				"sendStandardIO.eml");
		String content = "����ǵ��������򵥵Ĳ���";
		String mimeType = "text/html;charset=GBK";
		String subject = "����ǵ������򵥵Ĳ���";
		Map<String, String> from = new HashMap<String, String>();
		from.put("�Ժ決��", "taohongbei@yeah.net");
		Map<String, String> attchs = new HashMap<String, String>();
		attchs.put(
				"ͼƬ.jpg",
				FileUtility.getRelativeFileName(new String[] { "attchs",
						"404.jpg" }));
		attchs.put("�ļ�.txt",
				FileUtility.getRelativeFilePath("attchs", "attch.txt"));
		Translater.send(debug, subject, addresslist, desPath, content,
				mimeType, from, attchs);
	}

	@Test
	public void sendIOeml() {
		boolean debug = true;
		String host = "smtp.yeah.net";
		String username = "taohongbei@yeah.net";
		String password = "cxrhthb";
		String addresslist = "newangchunlei@163.com";
		String eml = FileUtility.getRelativeFilePath("des",
				"sendStandardIO.eml");
		Translater.send(debug, host, username, password, addresslist, eml);
	}
}
