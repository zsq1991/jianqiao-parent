package com.zc.common.core.email.receive;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-24 上午9:07:12
 * 
 */
public class EmailReceiveUtils {
	private EmailReceiveUtils() {
	}

	/**
	 * 邮件接收
	 * 
	 * @param popHost
	 * @param userName
	 * @param password
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static List<EmailParse> getMail(final String popHost, final String userName,
			final String password, String baseFileName) throws SocketException, IOException,
			MessagingException {
		List<EmailParse> emailParses = new ArrayList<EmailParse>();
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp." + StringUtils.substringAfter(popHost, "."));
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", popHost, 110, null, userName, password);
		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message msgs[] = folder.getMessages();
		ArrayUtils.reverse(msgs);
		for (Message message : msgs) {
			EmailParse emailParse = new EmailParse();
			ReciveMail reciveMail = new ReciveMail((MimeMessage) message);
			emailParse.setCc(reciveMail.getMailAddress("cc"));
			reciveMail.getMailContent(message);
			emailParse.setContent(reciveMail.getBodyText());
			emailParse.setHasAttchment(reciveMail.isContainAttch(message));
			emailParse.setMessageId(reciveMail.getMessageId());
			emailParse.setReceiveTime(reciveMail.getSendDate());
			emailParse.setReceiveUser(reciveMail.getMailAddress("to"));
			emailParse.setSendUser(reciveMail.getFrom());
			emailParse.setSubject(reciveMail.getSubject());
			emailParse.setReader(reciveMail.isNew());
			if (reciveMail.isContainAttch(message)) {
				reciveMail.saveAttchMent(baseFileName, message);
				emailParse.setFileName(StringUtils.split(reciveMail.getSaveAttchPath(), ","));
			}
			emailParses.add(emailParse);
		}
		store.close();
		return emailParses;
	}

	/**
	 * 获取最后一条邮件
	 * 
	 * @param popHost
	 * @param userName
	 * @param password
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static EmailParse getLastEmail(final String popHost, final String userName,
			final String password, String baseFileName) throws SocketException, IOException,
			MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp." + StringUtils.substringAfter(popHost, "."));
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", popHost, 110, null, userName, password);
		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message msgs[] = folder.getMessages();
		ArrayUtils.reverse(msgs);
		Message message = msgs[0];
		EmailParse emailParse = new EmailParse();
		ReciveMail reciveMail = new ReciveMail((MimeMessage) message);
		emailParse.setCc(reciveMail.getMailAddress("cc"));
		reciveMail.getMailContent(message);
		emailParse.setContent(reciveMail.getBodyText());
		emailParse.setHasAttchment(reciveMail.isContainAttch(message));
		emailParse.setMessageId(reciveMail.getMessageId());
		emailParse.setReceiveTime(reciveMail.getSendDate());
		emailParse.setReceiveUser(reciveMail.getMailAddress("to"));
		emailParse.setSendUser(reciveMail.getFrom());
		emailParse.setSubject(reciveMail.getSubject());
		emailParse.setReader(reciveMail.isNew());
		if (reciveMail.isContainAttch(message)) {
			reciveMail.saveAttchMent(baseFileName, message);
			emailParse.setFileName(StringUtils.split(reciveMail.getSaveAttchPath(), ","));
		}
		store.close();
		return emailParse;
	}

	/**
	 * 回去中间某条
	 * 
	 * @param popHost
	 * @param userName
	 * @param password
	 * @param num
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static EmailParse getEmailByNum(final String popHost, final String userName,
			final String password, int num, String baseFileName) throws SocketException,
			IOException, MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp." + StringUtils.substringAfter(popHost, "."));
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", popHost, 110, null, userName, password);
		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message msgs[] = folder.getMessages();
		ArrayUtils.reverse(msgs);
		Message message = msgs[num];
		EmailParse emailParse = new EmailParse();
		ReciveMail reciveMail = new ReciveMail((MimeMessage) message);
		emailParse.setCc(reciveMail.getMailAddress("cc"));
		reciveMail.getMailContent(message);
		emailParse.setContent(reciveMail.getBodyText());
		emailParse.setHasAttchment(reciveMail.isContainAttch(message));
		emailParse.setMessageId(reciveMail.getMessageId());
		emailParse.setReceiveTime(reciveMail.getSendDate());
		emailParse.setReceiveUser(reciveMail.getMailAddress("to"));
		emailParse.setSendUser(reciveMail.getFrom());
		emailParse.setSubject(reciveMail.getSubject());
		emailParse.setReader(reciveMail.isNew());
		if (reciveMail.isContainAttch(message)) {
			reciveMail.saveAttchMent(baseFileName, message);
			emailParse.setFileName(StringUtils.split(reciveMail.getSaveAttchPath(), ","));
		}
		store.close();
		return emailParse;
	}

	/**
	 * 获取邮件数量
	 * 
	 * @param popHost
	 * @param userName
	 * @param password
	 * @return
	 * @throws MessagingException
	 * @throws SocketException
	 * @throws IOException
	 */
	public static Integer getTotal(final String popHost, final String userName,
			final String password) throws MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp." + StringUtils.substringAfter(popHost, "."));
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", popHost, 110, null, userName, password);
		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message msgs[] = folder.getMessages();
		int count = msgs.length;
		store.close();
		return count;
	}
}
