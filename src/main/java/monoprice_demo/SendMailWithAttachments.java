package monoprice_demo;


import java.io.IOException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.monoprice.pageObjects.LoginPage;




public class SendMailWithAttachments {
	
	Properties prop = LoginPage.readproperties();

	public void sendingMail() {
	//public static void main(String[] args ) {
		String mail_Username = prop.getProperty("username");
		String mail_password = prop.getProperty("password");
		String from_mail = prop.getProperty("FromEmail");
		String to_mail = prop.getProperty("toemail");
		
		//authentication info
		final String username = mail_Username;
		final String password = mail_password;
		String fromEmail = from_mail;
		String toEmail = to_mail;
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject("Subject Line");
			
			Multipart emailContent = new MimeMultipart();
			
			//Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Final result of the executed testcases");
			
			//Attachment body part.
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile("/home/hussain/Documents/eclipse-workspace/monoprice_demo/test-output/ExtentReport.html");
			
			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(pdfAttachment);
			
			//Attach multipart to message
			msg.setContent(emailContent);
			
			Transport.send(msg);
			System.out.println("Sent message");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}