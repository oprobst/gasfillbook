package de.tsvmalsch.demo;

import java.util.Collection;

import org.hibernate.Session;

import de.tsvmalsch.server.HibernateUtil;
import de.tsvmalsch.shared.CreateDemoDataService;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

public class InitDBWithDemoData {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Collection<Member> member = CreateDemoDataService.createDummyMembers();

		for (Member m : member) {
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

		session.getTransaction().commit();
		System.out.println("Dumped Test data.");
	}
}
