package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.UserAuthenticationService;
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

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public Collection<Member> getAllMembers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		List<Member> members = new ArrayList<Member>(session.createQuery(
//				"from MEMBER").list());
//		session.getTransaction().commit();
		return Collections.EMPTY_LIST;
	}
}
