package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Query;
import org.hibernate.Session;
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

		return true;
	}

	@Override
	public Collection<Member> getAllMembers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("FROM de.tsvmalsch.shared.model.Member WHERE isActive = :isActive");
		query.setParameter("isActive", true);

		List<Member> members = new ArrayList<Member>(query.list());
		List<Member> serializableMembers = new ArrayList<>(members.size());

		session.getTransaction().commit();

		Mapper mapper = new DozerBeanMapper();
		for (Member m : members) {
			Member serialMember = mapper.map(m, Member.class);
			serializableMembers.add(serialMember);
		}

		return serializableMembers;
	}
}
