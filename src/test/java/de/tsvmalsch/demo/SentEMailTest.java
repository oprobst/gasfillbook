package de.tsvmalsch.demo;

import de.tsvmalsch.server.EMailServiceImpl;

public class SentEMailTest {
	public static void main(String[] args) {

		EMailServiceImpl email;
		try {
			email = new EMailServiceImpl();

			email.startEmailThread();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
