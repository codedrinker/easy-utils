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
	 * �򵥵�û�и��������䷢��
	 * 
	 * @param host
	 *            �������� ���׵�����:smtp.yeah.net
	 * @param username
	 *            ������û��� ���׵�����:mail_dev@yeah.net
	 * @param password
	 *            ��������� ���׵�����:mail_dev
	 * @param addresslist
	 *            ���͵ĵ�ַ�����Զ������,�ŷָ� ����:335365344@qq.com,swangchunlei@sina.com
	 * @param debug
	 *            �Ƿ���������ģʽ true��ӡ
	 * @param content
	 *            ���䷢�͵����ݣ������html��ʽ������<>��ǩ
	 * @param miniType
	 *            ���ݵ�mime���ͣ�����:text/html;charset=utf-8
	 * @param subject
	 *            �ʼ��ı��⣬����:�決���һ�����
	 * @param fromaddress
	 *            �ʼ�����Դ��һ�����Ϊ�����ַ ����:mail_dev@yeah.net
	 * @return �Ƿ��ͳɹ�
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

			// �����ʼ��ķ�������Ϣ
			// ����������"������ <335365344@qq.com>"��ʽ�ķ�������ʽ
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
	 * ���ز��Ե��ʼ��������������浽�����ļ�ϵͳ��
	 * 
	 * @param debug
	 *            boolean �Ƿ���������ģʽ true��ӡ
	 * @param subject
	 *            String �ʼ��ı��⣬����:�決���һ�����
	 * @param addresslist
	 *            String ���͵ĵ�ַ�����Զ������,�ŷָ� ����:335365344@qq.com,swangchunlei@sina.com
	 * @param desPath
	 *            String ����eml�ļ��ľ���·��
	 * @param content
	 *            String ���䷢�͵����ݣ������html��ʽ������<>��ǩ
	 * @param mimeType
	 *            String ���ݵ�mini���ͣ�����:text/html;charset=utf-8
	 * @param from
	 *            Map �ʼ�����Դ������������"������  <335365344@qq.com>"��ʽ�ķ�������ʽ,�������ﴫ�ݵ���Map���ͣ�<br />
	 *            key�Ƿ����˵�������value�Ƿ����˵������ַ
	 * @param attchs
	 *            Map ����ĸ��� key�Ǿ���ĸ������ƣ��������չ��������:����.JPG,value�Ǿ���ĸ����Ĵ���ϵͳ�ľ���·�� d:/����.JPG
	 * @return boolean �Ƿ��ͳɹ�
	 */
	public static boolean send(boolean debug, String subject,
			String addresslist, String desPath, String content,
			String mimeType, Map<String, String> from,
			Map<String, String> attchs) {
		try {
			// �õ�session
			Session session = Session.getInstance(new Properties());
			// ����msg
			Message msg = new MimeMessage(session);
			// ���ÿ��Ե���ģʽ
			session.setDebug(true);

			// ��������͵�multipart
			MimeMultipart msgmultipart = new MimeMultipart("mixed");
			// ѭ������ļ����ʼ��ĸ�����
			// ǰ����Ǹ��������֣�������Ǹ����ľ���·��
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
			// Ϊ�ʼ����������

			// �����������Ĳ���
			MimeBodyPart bodycontent = new MimeBodyPart();

			// ��Ӿ����html��Ϣ
			bodycontent.setContent(content, mimeType);

			// �����ķŵ�������
			msgmultipart.addBodyPart(bodycontent);

			// �������б༭�����ݷ���msg��
			msg.setContent(msgmultipart);

			msg.setSubject(MimeUtility.decodeText(subject));

			// �����ʼ��ķ�������Ϣ
			// ����������"������ <335365344@qq.com>"��ʽ�ķ�������ʽ
			if (from != null && !from.isEmpty()) {
				for (String name : from.keySet()) {
					msg.setFrom(new InternetAddress("\""
							+ MimeUtility.encodeText(name) + "\"" + " <"
							+ from.get(name) + ">"));
				}
			}

			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			// ����仯
			// ��һ�γ����ˣ�����仯Ӧ�÷��ڷ������ݵĺ���
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
	 * ���и������ʼ�����
	 * 
	 * @param debug
	 *            boolean �Ƿ���������ģʽ true��ӡ
	 * @param host
	 *            String �������� ���׵�����:smtp.yeah.net
	 * @param username
	 *            String ������û��� ���׵�����:mail_dev@yeah.net
	 * @param password
	 *            String ��������� ���׵�����:mail_dev
	 * @param subject
	 *            String �ʼ��ı��⣬����:�決���һ�����
	 * @param addresslist
	 *            String ���͵ĵ�ַ�����Զ������,�ŷָ� ����:335365344@qq.com,swangchunlei@sina.com
	 * @param content
	 *            String ���䷢�͵����ݣ������html��ʽ������<>��ǩ
	 * @param mimeType
	 *            String ���ݵ�mini���ͣ�����:text/html;charset=utf-8
	 * @param from
	 *            Map �ʼ�����Դ������������"������  <335365344@qq.com>"��ʽ�ķ�������ʽ,�������ﴫ�ݵ���Map���ͣ�<br />
	 *            key�Ƿ����˵�������value�Ƿ����˵������ַ
	 * @param attchs
	 *            Map ����ĸ��� key�Ǿ���ĸ������ƣ��������չ��������:����.JPG,value�Ǿ���ĸ����Ĵ���ϵͳ�ľ���·�� d:/����.JPG
	 * @return boolean �Ƿ��ͳɹ�
	 */
	public static boolean send(boolean debug, String host,
			final String username, final String password, String subject,
			String addresslist, String content, String mimeType,
			Map<String, String> from, Map<String, String> attchs) {
		try {

			// ��֤�����û���������
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
			// ���ÿ��Ե���ģʽ
			session.setDebug(true);

			// ��������͵�multipart
			MimeMultipart msgmultipart = new MimeMultipart("mixed");
			// ѭ������ļ����ʼ��ĸ�����
			// ǰ����Ǹ��������֣�������Ǹ����ľ���·��
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
			// Ϊ�ʼ����������

			// �����������Ĳ���
			MimeBodyPart bodycontent = new MimeBodyPart();

			// ��Ӿ����html��Ϣ
			bodycontent.setContent(content, mimeType);

			// �����ķŵ�������
			msgmultipart.addBodyPart(bodycontent);

			// �������б༭�����ݷ���msg��
			msg.setContent(msgmultipart);

			msg.setSubject(MimeUtility.decodeText(subject));

			// �����ʼ��ķ�������Ϣ
			// ����������"������ <335365344@qq.com>"��ʽ�ķ�������ʽ
			if (from != null && !from.isEmpty()) {
				for (String name : from.keySet()) {
					msg.setFrom(new InternetAddress("\""
							+ MimeUtility.encodeText(name) + "\"" + " <"
							+ from.get(name) + ">"));
				}
			}

			msg.setRecipients(RecipientType.TO,
					InternetAddress.parse(addresslist));
			// ����仯
			// ��һ�γ����ˣ�����仯Ӧ�÷��ڷ������ݵĺ���
			msg.saveChanges();
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ���ڷ��ͱ����Ѿ�����eml�ļ��ķ��ͷ�ʽ
	 * 
	 * @param debug
	 *            boolean �Ƿ���������ģʽ true��ӡ
	 * @param host
	 *            String �������� ���׵�����:smtp.yeah.net
	 * @param username
	 *            String ������û��� ���׵�����:mail_dev@yeah.net
	 * @param password
	 *            String ��������� ���׵�����:mail_dev
	 * @param addresslist
	 *            String ���͵ĵ�ַ�����Զ������,�ŷָ� ����:335365344@qq.com,swangchunlei@sina.com
	 * @param eml
	 *            String ����ı���·��d:/����.eml
	 * @return boolean �Ƿ��ͳɹ�
	 */
	public static boolean send(boolean debug, String host,
			final String username, final String password, String addresslist,
			String eml) {
		try {
			// ��֤�����û���������
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
			// ���ÿ��Ե���ģʽ
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
