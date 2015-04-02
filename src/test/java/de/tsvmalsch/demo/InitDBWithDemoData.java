package de.tsvmalsch.demo;

import java.util.Date;
import java.util.HashSet;

import org.hibernate.Session;

import de.tsvmalsch.server.HibernateUtil;
import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

public class InitDBWithDemoData {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		for (int i = 0; i < 100; i++) {
			Member m = new Member();
			m.setFirstName("F" + i);
			m.setLastName("L" + i);
			m.setMemberNumber(i);
			m.setEncodedPassword("1");
			m.setEmail("me@home.de");
			session.save(m);

			Cylinder cy = new Cylinder();
			cy.setName("D12-a");
			cy.setMaximumPreasure(300);
			cy.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
					* 60 * 24 * 360 * 1000));
			cy.setSizeInLiter(12.0f);
			cy.setOwner(m);
			cy.setSerialNumber("0000124124");
			Cylinder cy2 = new Cylinder();
			cy2.setName("D12-a");
			cy2.setMaximumPreasure(300);
			cy2.setNextInspectionDate(new Date(System.currentTimeMillis()
					+ 1000 * 60 * 24 * 360 * 1000));
			cy2.setSizeInLiter(12.0f);
			cy2.setOwner(m);
			cy2.setSerialNumber("0000124125");
			cy2.setTwinSetPartner(cy);

			session.save(cy2);
			session.save(cy);
			for (int j = 0; j < 5; j++) {
				Cylinder cy3 = new Cylinder();
				cy3.setName("D12-a");
				cy3.setMaximumPreasure(232);
				cy3.setNextInspectionDate(new Date(System.currentTimeMillis()
						+ 1000 * 60 * 24 * (360 - j * 10) * 1000));
				cy3.setSizeInLiter(11.1f);
				cy3.setOwner(m);
				cy3.setSerialNumber("MES123");
				session.save(cy3);
			}

			for (int j = 0; j < 3; j++) {
				FillingInvoiceItem fii = new FillingInvoiceItem();
				fii.setBlendingType(BlendingType.AIR);
				fii.setCreditor(m);
				fii.setDateOfFilling(new Date(System.currentTimeMillis() - j
						* 1000 * 60 * 60 * 24 * 2));
				HashSet<Cylinder> tank = new HashSet<Cylinder>();
				tank.add(cy);
				fii.setFilledCylinder(tank);
				session.save(fii);
			}
		}

	 

		session.getTransaction().commit();
		System.out.println("Dumped Test data.");
	}
}
