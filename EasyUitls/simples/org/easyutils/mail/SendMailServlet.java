package org.easyutils.mail;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMailServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Context initCtx;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Session session = (Session) envCtx.lookup("mail/Session");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("taohongbei@yeah.net"));
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress("newangchunlei@163.com");
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject("用JNDI发一个邮件看看行不行");
			message.setContent("你好", "text/plain");
			Transport transport = session.getTransport();
			/*
			 * 
			 * String host = "smtp.yeah.net"; String username = "taohongbei@yeah.net"; String password = "cxrhthb";
			 */
			transport
					.connect("smtp.yeah.net", "taohongbei@yeah.net", "cxrhthb");
			transport.sendMessage(message, to);
			transport.close();
			response.getWriter().print("ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(response.getWriter());
		}

	}

}
