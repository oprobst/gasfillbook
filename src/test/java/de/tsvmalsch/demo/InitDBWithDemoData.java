package de.tsvmalsch.demo;

import java.util.Collection;

import org.hibernate.Session;

import de.tsvmalsch.server.HibernateUtil;
import de.tsvmalsch.shared.CreateDemoDataService;
import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

public class InitDBWithDemoData {

	public static void main(String[] args) {
		try {
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();
			session.beginTransaction();

			Configuration currConfig = new Configuration();

			Collection<Member> member = CreateDemoDataService
					.createDummyMembers();

			int i = 0;
			currConfig.setAdministrators("110");
			currConfig.setBlendingInstructors("112");
			currConfig.setInvoiceNotificationMails("111");
			for (Member m : member) {

				if (i++ < 6) {
					currConfig.setAdministrators(currConfig.getAdministrators()
							+ "," + m.getMemberNumber());
					currConfig.setBlendingInstructors(currConfig
							.getBlendingInstructors()
							+ ","
							+ m.getMemberNumber());
				}
				session.save(m);
				for (Cylinder c : m.getCylinders()) {
					session.save(c);
				}
				Collection<FillingInvoiceItem> fii = CreateDemoDataService
						.createDummyInvoiceItem(m);
				for (FillingInvoiceItem f : fii) {
					session.save(f);
				}

			}

			session.save(currConfig);
			session.getTransaction().commit();
			System.out.println("Dumped Test data.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
