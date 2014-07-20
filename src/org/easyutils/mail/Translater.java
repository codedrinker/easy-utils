package org.easyutils.mail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

/**
 * 
 * @author Primos
 * 
 */
public class Translater {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Translater.class);

	/**
	 * 简单的没有附件的邮箱发送
	 * 
	 * @param host
	 *            邮箱主机 网易的例如:smtp.yeah.net
	 * @param username
	 *            邮箱的用户名 网易的例如:mail_dev@yeah.net
	 * @param password
	 *            邮箱的密码 网易的例如:mail_dev
	 * @param addresslist
	 *            发送的地址，可以多个，用,号分隔 例如:335365344@qq.com,swangchunlei@sina.com
	 * @param debug
	 *            是否启动调试模式 true打印
	 * @param content
	 *            邮箱发送的内容，如果是html格式可以用<>标签
	 * @param miniType
	 *            内容的mime类型，例如:text/html;charset=utf-8
	 * @param subject
	 *            邮件的标题，例如:烘焙网找回密码
	 * @param fromaddress
	 *            邮件的来源，一般添加为邮箱地址 例如:mail_dev@yeah.net
	 * @return 是否发送成功
	 */

	public static boolean send(String host, final String username,
			final String password, String addresslist, boolean debug,
			String content, String mimeType, String subject,
			Map<String, String> from) {
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", host);
			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}

			});
			session.setDebug(true);
			Message msg = new MimeMessage(session);
			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			msg.setContent(content, mimeType);
			msg.setSubject(MimeUtility.decodeText(subject));

			// 设置邮件的发件人信息
			// 设置类似于"王春磊 <335365344@qq.com>"格式的发件人形式
			if (from != null && !from.isEmpty()) {
				for (String name : from.keySet()) {
					msg.setFrom(new InternetAddress("\""
							+ MimeUtility.encodeText(name) + "\"" + " <"
							+ from.get(name) + ">"));
				}
			}

			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 本地测试的邮件生产方法，保存到本地文件系统中
	 * 
	 * @param debug
	 *            boolean 是否启动调试模式 true打印
	 * @param subject
	 *            String 邮件的标题，例如:烘焙网找回密码
	 * @param addresslist
	 *            String 发送的地址，可以多个，用,号分隔 例如:335365344@qq.com,swangchunlei@sina.com
	 * @param desPath
	 *            String 生成eml文件的绝对路径
	 * @param content
	 *            String 邮箱发送的内容，如果是html格式可以用<>标签
	 * @param mimeType
	 *            String 内容的mini类型，例如:text/html;charset=utf-8
	 * @param from
	 *            Map 邮件的来源，设置类似于"王春磊  <335365344@qq.com>"格式的发件人形式,所以这里传递的是Map类型，<br />
	 *            key是发件人的姓名，value是发件人的邮箱地址
	 * @param attchs
	 *            Map 邮箱的附件 key是具体的附件名称，需添加扩展名，例如:附件.JPG,value是具体的附件的磁盘系统的绝对路径 d:/附件.JPG
	 * @return boolean 是否发送成功
	 */
	public static boolean send(boolean debug, String subject,
			String addresslist, String desPath, String content,
			String mimeType, Map<String, String> from,
			Map<String, String> attchs) {
		try {
			// 得到session
			Session session = Session.getInstance(new Properties());
			// 创建msg
			Message msg = new MimeMessage(session);
			// 设置可以调试模式
			session.setDebug(true);

			// 创建混合型的multipart
			MimeMultipart msgmultipart = new MimeMultipart("mixed");
			// 循环添加文件到邮件的附件中
			// 前面的是附件的名字，后面的是附件的绝对路径
			if (attchs != null && !attchs.isEmpty()) {
				for (String name : attchs.keySet()) {
					MimeBodyPart attchpart = new MimeBodyPart();
					DataSource ds = new FileDataSource(attchs.get(name));
					DataHandler dh = new DataHandler(ds);
					attchpart.setDataHandler(dh);
					attchpart.setFileName(MimeUtility.encodeText(name));
					msgmultipart.addBodyPart(attchpart);
				}
			}
			// 为邮件体添加正文

			// 定义整个正文部分
			MimeBodyPart bodycontent = new MimeBodyPart();

			// 添加具体的html信息
			bodycontent.setContent(content, mimeType);

			// 把正文放到整体中
			msgmultipart.addBodyPart(bodycontent);

			// 最后把所有编辑的内容放入msg中
			msg.setContent(msgmultipart);

			msg.setSubject(MimeUtility.decodeText(subject));

			// 设置邮件的发件人信息
			// 设置类似于"王春磊 <335365344@qq.com>"格式的发件人形式
			if (from != null && !from.isEmpty()) {
				for (String name : from.keySet()) {
					msg.setFrom(new InternetAddress("\""
							+ MimeUtility.encodeText(name) + "\"" + " <"
							+ from.get(name) + ">"));
				}
			}

			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			// 保存变化
			// 第一次出错了，保存变化应该放在放入内容的后面
			msg.saveChanges();

			OutputStream ips = new FileOutputStream(desPath);
			msg.writeTo(ips);
			ips.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 带有附件的邮件发送
	 * 
	 * @param debug
	 *            boolean 是否启动调试模式 true打印
	 * @param host
	 *            String 邮箱主机 网易的例如:smtp.yeah.net
	 * @param username
	 *            String 邮箱的用户名 网易的例如:mail_dev@yeah.net
	 * @param password
	 *            String 邮箱的密码 网易的例如:mail_dev
	 * @param subject
	 *            String 邮件的标题，例如:烘焙网找回密码
	 * @param addresslist
	 *            String 发送的地址，可以多个，用,号分隔 例如:335365344@qq.com,swangchunlei@sina.com
	 * @param content
	 *            String 邮箱发送的内容，如果是html格式可以用<>标签
	 * @param mimeType
	 *            String 内容的mini类型，例如:text/html;charset=utf-8
	 * @param from
	 *            Map 邮件的来源，设置类似于"王春磊  <335365344@qq.com>"格式的发件人形式,所以这里传递的是Map类型，<br />
	 *            key是发件人的姓名，value是发件人的邮箱地址
	 * @param attchs
	 *            Map 邮箱的附件 key是具体的附件名称，需添加扩展名，例如:附件.JPG,value是具体的附件的磁盘系统的绝对路径 d:/附件.JPG
	 * @return boolean 是否发送成功
	 */
	public static boolean send(boolean debug, String host,
			final String username, final String password, String subject,
			String addresslist, String content, String mimeType,
			Map<String, String> from, Map<String, String> attchs) {
		try {

			// 验证邮箱用户名和密码
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", host);
			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}

			});

			Message msg = new MimeMessage(session);
			// 设置可以调试模式
			session.setDebug(true);

			// 创建混合型的multipart
			MimeMultipart msgmultipart = new MimeMultipart("mixed");
			// 循环添加文件到邮件的附件中
			// 前面的是附件的名字，后面的是附件的绝对路径
			if (attchs != null && !attchs.isEmpty()) {
				for (String name : attchs.keySet()) {
					MimeBodyPart attchpart = new MimeBodyPart();
					DataSource ds = new FileDataSource(attchs.get(name));
					DataHandler dh = new DataHandler(ds);
					attchpart.setDataHandler(dh);
					attchpart.setFileName(MimeUtility.encodeText(name));
					msgmultipart.addBodyPart(attchpart);
				}
			}
			// 为邮件体添加正文

			// 定义整个正文部分
			MimeBodyPart bodycontent = new MimeBodyPart();

			// 添加具体的html信息
			bodycontent.setContent(content, mimeType);

			// 把正文放到整体中
			msgmultipart.addBodyPart(bodycontent);

			// 最后把所有编辑的内容放入msg中
			msg.setContent(msgmultipart);

			msg.setSubject(MimeUtility.decodeText(subject));

			// 设置邮件的发件人信息
			// 设置类似于"王春磊 <335365344@qq.com>"格式的发件人形式
			if (from != null && !from.isEmpty()) {
				for (String name : from.keySet()) {
					msg.setFrom(new InternetAddress("\""
							+ MimeUtility.encodeText(name) + "\"" + " <"
							+ from.get(name) + ">"));
				}
			}

			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			// 保存变化
			// 第一次出错了，保存变化应该放在放入内容的后面
			msg.saveChanges();
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 用于发送本地已经存在eml文件的发送方式
	 * 
	 * @param debug
	 *            boolean 是否启动调试模式 true打印
	 * @param host
	 *            String 邮箱主机 网易的例如:smtp.yeah.net
	 * @param username
	 *            String 邮箱的用户名 网易的例如:mail_dev@yeah.net
	 * @param password
	 *            String 邮箱的密码 网易的例如:mail_dev
	 * @param addresslist
	 *            String 发送的地址，可以多个，用,号分隔 例如:335365344@qq.com,swangchunlei@sina.com
	 * @param eml
	 *            String 邮箱的本地路径d:/附件.eml
	 * @return boolean 是否发送成功
	 */
	public static boolean send(boolean debug, String host,
			final String username, final String password, String addresslist,
			String eml) {
		try {
			// 验证邮箱用户名和密码
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", host);
			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}

			});

			Message msg = new MimeMessage(session, new FileInputStream(eml));
			// 设置可以调试模式
			session.setDebug(true);

			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
