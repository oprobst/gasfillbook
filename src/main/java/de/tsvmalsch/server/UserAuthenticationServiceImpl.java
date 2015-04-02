package de.tsvmalsch.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.UserAuthenticationService;
import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

@SuppressWarnings("serial")
public class UserAuthenticationServiceImpl extends RemoteServiceServlet
		implements UserAuthenticationService {

	Logger log = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

	public UserAuthenticationServiceImpl() throws Exception {
		// A SessionFactory is set up once for an application

	}

	@Override
	public boolean authenticate(int memberNumber, String encodedPassword)
			throws IllegalArgumentException {

		Member m = new Member();
		m.setFirstName("Oliver");
		m.setLastName("Probst");
		m.setMemberNumber(memberNumber);
		m.setEncodedPassword(encodedPassword);
		m.setEmail("me@home.de"); 

		Cylinder cy = new Cylinder();
		cy.setName("D12-a");
		cy.setMaximumPreasure(232);
		cy.setNextInspectionDate(new Date(System.currentTimeMillis()));
		cy.setSizeInLiter(11.1f);
		cy.setOwner(m);
		cy.setSerialNumber("0000124124");

		FillingInvoiceItem fii = new FillingInvoiceItem();
		fii.setBlendingType(BlendingType.AIR);
		fii.setCreditor(m);
		fii.setDateOfFilling(new Date());
		HashSet<Cylinder> mono12 = new HashSet<Cylinder>();
		mono12.add(cy);
		fii.setFilledCylinder(mono12);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(m);
session.save(fii);
session.save(cy);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public Collection<Member> getAllMembers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// session.beginTransaction();
		// List<Member> members = new ArrayList<Member>(session.createQuery(
		// "from MEMBER").list());
		// session.getTransaction().commit();
		return Collections.EMPTY_LIST;
	}
}
