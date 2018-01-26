package com.zc.common.core.email.receive;

import com.zc.common.core.utils.UniqueUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-7-17 下午8:17:55
 * 
 */
public class ReciveMail {
	private MimeMessage msg = null;
	private String saveAttchPath = "";
	private StringBuffer bodytext = new StringBuffer();
	private String dateformate = "yyyy-MM-dd HH:mm:ss";
	String[] filenames = new String[] {};

	public ReciveMail(MimeMessage msg) {
		this.msg = msg;
	}

	public void setMsg(MimeMessage msg) {
		this.msg = msg;
	}

	/**
	 * 获取发送邮件者信息
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getFrom() throws MessagingException {
		InternetAddress[] address = (InternetAddress[]) msg.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = address[0].getPersonal();
		if (personal == null) {
			personal = "";
		}
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * 获取邮件收件人，抄送，密送的地址和信息。根据所传递的参数不同 "to"-->收件人,"cc"-->抄送人地址,"bcc"-->密送地址
	 * 
	 * @param type
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public String getMailAddress(String type) throws MessagingException,
			UnsupportedEncodingException {
		String mailaddr = "";
		String addrType = type.toUpperCase();
		InternetAddress[] address = null;
		String to = "TO";
		String cc = "CC";
		String bcc = "BCC";
		if (to.equals(addrType) || cc.equals(addrType) || bcc.equals(addrType)) {
			if (to.equals(addrType)) {
				address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.TO);
			}
			if (cc.equals(addrType)) {
				address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.CC);
			}
			if (bcc.equals(addrType)) {
				address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.BCC);
			}

			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String mail = address[i].getAddress();
					if (mail == null) {
						mail = "";
					} else {
						mail = MimeUtility.decodeText(mail);
					}
					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + mail + ">";
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new RuntimeException("Error email Type!");
		}
		return mailaddr;
	}

	/**
	 * 获取邮件主题
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public String getSubject() throws UnsupportedEncodingException, MessagingException {
		String subject = "";
		subject = MimeUtility.decodeText(msg.getSubject());
		if (subject == null) {
			subject = "";
		}
		return subject;
	}

	/**
	 * 获取邮件发送日期
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getSendDate() throws MessagingException {
		Date sendDate = msg.getSentDate();
		SimpleDateFormat smd = new SimpleDateFormat(dateformate);
		return smd.format(sendDate);
	}

	/**
	 * 获取邮件正文内容
	 * 
	 * @return
	 */
	public String getBodyText() {

		return bodytext.toString();
	}

	/**
	 * 解析邮件，将得到的邮件内容保存到一个stringBuffer对象中，解析邮件 主要根据MimeType的不同执行不同的操作，一步一步的解析
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void getMailContent(Part part) throws MessagingException, IOException {

		String contentType = part.getContentType();
		int nameindex = contentType.indexOf("name");
		boolean conname = false;
		if (nameindex != -1) {
			conname = true;
		}
		String textPlain="text/plain";
		String textHtml="text/html";
		String multipartType="multipart/*";
		String messageType="message/rfc822";
		if (part.isMimeType(textPlain) && !conname) {
			bodytext.append((String) part.getContent());
		} else if (part.isMimeType(textHtml) && !conname) {
			bodytext.append((String) part.getContent());
		} else if (part.isMimeType(multipartType)) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType(messageType)) {
			getMailContent((Part) part.getContent());
		}

	}

	/**
	 * 判断邮件是否需要回执，如需回执返回true，否则返回false
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replySign = false;
		String[] needreply = msg.getHeader("Disposition-Notification-TO");
		if (needreply != null) {
			replySign = true;
		}
		return replySign;
	}

	/**
	 * 获取此邮件的message-id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getMessageId() throws MessagingException {
		return msg.getMessageID();
	}

	/**
	 * 判断此邮件是否已读，如果未读则返回false，已读返回true
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) msg).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}

		return isnew;
	}

	/**
	 * 判断是是否包含附件
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public boolean isContainAttch(Part part) throws MessagingException, IOException {
		boolean flag = false;
		String multipartType="multipart/*";
		String messageType="message/rfc822";
		if (part.isMimeType(multipartType)) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();

			for (int i = 0; i < count; i++) {
				BodyPart bodypart = multipart.getBodyPart(i);
				String dispostion = bodypart.getDisposition();
				boolean dispostionb = false;
				/*if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE))){
					dispostionb = true;
				}*/
				if(dispostion != null){
					dispostionb = true;
				}
				if (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE)){
					dispostionb = true;
				}

				if (dispostionb) {
					flag = true;
				} else if (bodypart.isMimeType("multipart/*")) {
					flag = isContainAttch(bodypart);
				} else {
					String conType = bodypart.getContentType();
					boolean conTypeb = false;
					if(conType.toLowerCase().indexOf("appliaction") != -1){
						conTypeb = true;
					}
					if (conTypeb) {
						flag = true;
					}
					boolean nameb = false;
					if (conType.toLowerCase().indexOf("name") != -1){
						nameb = true;
					}
					if (nameb) {
						flag = true;
					}
				}
			}
		} else if (part.isMimeType(messageType)) {
			flag = isContainAttch((Part) part.getContent());
		}

		return flag;
	}

	/**
	 * 保存附件
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void saveAttchMent(String baseFile, Part part) throws MessagingException, IOException {
		String filename = "";
		String fileName = "";
		String multipartType="multipart/*";
		String messageType="message/rfc822";
		if (part.isMimeType(multipartType)) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String dispostion = mpart.getDisposition();
				boolean dispostionb = false;
				/*if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE))){
					dispostionb = true;
				}*/
				if(dispostion != null){
					dispostionb = true;
				}
				if (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE)){
					dispostionb = true;
				}

				if (dispostionb) {
					filename = mpart.getFileName();
					if (StringUtils.isNotBlank(filename)) {
						filename = MimeUtility.decodeText(filename);
						fileName = UniqueUtils.getOrder() + "/";
						filenames = ArrayUtils.add(filenames, fileName + filename);
						setSaveAttchPath(StringUtils.join(filenames, ","));
						saveFile(baseFile + fileName + filename, mpart.getInputStream());
					}
				} else if (mpart.isMimeType("multipart/*")) {
					saveAttchMent(baseFile, mpart);
				} else {
					filename = mpart.getFileName();
					if (StringUtils.isNotBlank(filename)) {
						filename = MimeUtility.decodeText(filename);
						fileName = UniqueUtils.getOrder() + "/";
						filenames = ArrayUtils.add(filenames, fileName + filename);
						setSaveAttchPath(StringUtils.join(filenames, ","));
						saveFile(baseFile + fileName + filename, mpart.getInputStream());
					}
				}
			}

		} else if (part.isMimeType(messageType)) {
			saveAttchMent(baseFile, (Part) part.getContent());
		}
	}

	/**
	 * 获得保存附件的地址
	 * 
	 * @return
	 */
	public String getSaveAttchPath() {
		return saveAttchPath;
	}

	/**
	 * 设置保存附件地址
	 * 
	 * @param saveAttchPath
	 */
	public void setSaveAttchPath(String saveAttchPath) {
		this.saveAttchPath = saveAttchPath;
	}

	/**
	 * 设置日期格式
	 * 
	 * @param dateformate
	 */
	public void setDateformate(String dateformate) {
		this.dateformate = dateformate;
	}

	/**
	 * 保存文件内容
	 * 
	 * @param filename
	 * @param inputStream
	 * @throws IOException
	 */
	private void saveFile(String filename, InputStream inputStream) throws IOException {
		FileUtils.writeByteArrayToFile(new File(filename), IOUtils.toByteArray(inputStream));
		IOUtils.closeQuietly(inputStream);
	}

	public void recive(Part part, int i) throws MessagingException, IOException {
		System.out.println("------------------START-----------------------");
		System.out.println("Message" + i + " subject:" + getSubject());
		System.out.println("Message" + i + " from:" + getFrom());
		System.out.println("Message" + i + " isNew:" + isNew());
		boolean flag = isContainAttch(part);
		System.out.println("Message" + i + " isContainAttch:" + flag);
		System.out.println("Message" + i + " replySign:" + getReplySign());
		System.out.println("Message" + i + " messageId:" + getMessageId());
		getMailContent(part);
		System.out.println("Message" + i + " content:" + getBodyText());
		System.out.println("Message" + i + " date" + getSendDate());
		setSaveAttchPath("D:\\attachment\\" + i);
		if (flag) {
			// saveAttchMent(part);
		}
		System.out.println("------------------END-----------------------");
	}

	public static void main(String[] args) throws MessagingException, IOException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", "pop.qq.com", 110, null, "627658539",
				"hxhx350782zxm,./");
		Store store = session.getStore(urlname);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message[] msgs = folder.getMessages();
		int count = msgs.length;
		System.out.println("Message Count:" + count);
		ReciveMail rm = null;
		for (int i = 0; i < count; i++) {
			rm = new ReciveMail((MimeMessage) msgs[i]);
			rm.recive(msgs[i], i);
			;
		}
	}
}
