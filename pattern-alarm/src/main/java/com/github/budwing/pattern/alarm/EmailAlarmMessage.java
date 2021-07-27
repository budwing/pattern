package com.github.budwing.pattern.alarm;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAlarmMessage extends AlarmMessage {
	private String smtpHost;
	private String emailUser;
	private String emailPassword;
	private int emailRetryTimes = 3;
	
	public String build() {
		StringBuilder emailContent = new StringBuilder("�𾴵��쵼��<br/><br/>");
		emailContent.append("&nbsp;&nbsp;&nbsp;&nbsp;<font color=red>")
				.append("��ֹ��").append(getTimeFormat().format(getTime())).append("��<b>��")
				.append(getDistrict()).append(getDuration()).append("���ڷ�����")
				.append(getPatientNumber()).append("��").append(getDesease())
				.append("��Ⱦ����</b>���������ѳ�����ʷͬ��ˮƽ����ע������������С�<br/>")
				.append("</font><br/><br/><br/>���ʼ���ϵͳ�Զ����ɣ����ػظ���");
		return emailContent.toString();
	}
	
	public boolean send(List<Employee> contacts) {
		// �����ʼ�
		String emailContent = build();
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", smtpHost);
		prop.setProperty("mail.smtp.user", emailUser);
		prop.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop, new SmtpUPAuth(emailUser,
				emailPassword));
		InternetAddress[] address = new InternetAddress[contacts.size()];
		int index = 0;
		for (Employee e : contacts) {
			try {
				address[index] = new InternetAddress(e.getEmail());
				index++;
			} catch (AddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		for (int i = 0; i < emailRetryTimes; i++) {
			try {
				Message mailMsg = new MimeMessage(session);
				mailMsg.setFrom(new InternetAddress(emailUser));
				mailMsg.setSubject("Ⱥ���Լ���Ԥ��");
				mailMsg.setContent(emailContent.toString(),
						"text/html;charset=utf-8");
				mailMsg.setRecipients(RecipientType.TO, address);

				Transport.send(mailMsg);
				log(contacts, 2, emailContent.toString(), "success");
				break;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				log(contacts, 2, emailContent.toString(), "failure");
			}
		}
		
		return true;
	}
}

class SmtpUPAuth extends Authenticator {
	private String username;
	private String password;

	public SmtpUPAuth(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(username, password);
	}

}
