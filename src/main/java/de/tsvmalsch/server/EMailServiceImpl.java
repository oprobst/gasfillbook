package de.tsvmalsch.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.mail.smtp.SMTPTransport;

import de.tsvmalsch.client.EMailService;

@SuppressWarnings("serial")
public class EMailServiceImpl extends RemoteServiceServlet implements
		EMailService {

	Logger log = LoggerFactory.getLogger(EMailServiceImpl.class);

	public EMailServiceImpl() throws Exception {

	}

	@Override
	public void startEmailThread() {

		Thread sendMailThread = new Thread() {

			public synchronized void start() {
				try {
					sendMail();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		sendMailThread.run();

	}

	public boolean sendMail(/* Member m, Collection<FillingInvoiceItem> bill */)
			throws MessagingException {

		String to = "OliverProbst@gmx.de";

		// Sender's email ID needs to be mentioned
		String from = "testaccount@tief-dunkel-kalt.org";

		// Assuming you are sending email from localhost
		String host = "smtp.1und1.de";

		// Get system properties
		Properties properties = System.getProperties();

		try {
			// Setup mail server
			if (properties.get("mail.smtp.host") == null) {
				properties.setProperty("mail.smtp.host", host);
			}
			if (properties.get("mail.user") == null) {
				properties.setProperty("mail.user",
						"testaccount@tief-dunkel-kalt.org");
			}
			if (properties.get("mail.password") == null) {
				throw new MessagingException(
						"No password provided. Please define the system properties 'mail.password' and 'mail.user'.");
			}
			properties.setProperty("mail.smtps.auth", "true");

			Session session = Session.getDefaultInstance(properties);

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setHeader("X-Mailer", "Füllstation TSV-Malsch");

			message.setSubject("This is the Subject Line!");

			message.setText("This is the actual message");

			SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
			t.connect(properties.get("mail.smtp.host").toString(), properties
					.get("mail.user").toString(),
					properties.get("mail.password").toString());

			t.sendMessage(message, message.getAllRecipients());


			log.info("Send message!");
		} catch (MessagingException mex) {
			log.error("Could not send Mail", mex);

			/*
			 * log.error("Could not send message to Member " +
			 * m.getMemberNumber() + " (" + m.getEmail() + ").", mex);
			 */

			throw mex;
		}
		return true;
	}
}
