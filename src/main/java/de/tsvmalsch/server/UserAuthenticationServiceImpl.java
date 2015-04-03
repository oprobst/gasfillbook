package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		Member m = new Member();
		m.setFirstName("Oliver");
		m.setLastName("Probst");
		m.setMemberNumber(1);
		m.setEncodedPassword("1");
		m.setEmail("me@tsv-malsch.de");
		m.setIsActive(true);
		m.setHasGasblenderBrevet(true);
		allMembers.put("Oliver Probst", m);

		m = new Member();
		m.setFirstName("Oliver");
		m.setLastName("Patzelt");
		m.setMemberNumber(2);
		m.setEncodedPassword("1");
		m.setEmail("me@tsv-malsch.de");
		m.setIsActive(true);
		m.setHasGasblenderBrevet(true);
		allMembers.put("Oliver Patzelt", m);

		m = new Member();
		m.setFirstName("Olivia");
		m.setLastName("Patzelt");
		m.setMemberNumber(3);
		m.setEncodedPassword("1");
		m.setEmail("me@tsv-malsch.de");
		m.setIsActive(true);
		m.setHasGasblenderBrevet(true);
		allMembers.put("Olivia Patzelt", m);

		if (true)
			return;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("FROM de.tsvmalsch.shared.model.Member WHERE isActive = :isActive");
		query.setParameter("isActive", true);

		List<Member> members = new ArrayList<Member>(query.list());

		session.getTransaction().commit();

		Mapper mapper = new DozerBeanMapper();
		for (Member member : members) {
			Member serialMember = mapper.map(member, Member.class);

			String name = member.getFirstName() + " " + member.getLastName();
			allMembers.put(name, serialMember);
		}

	}

	@Override
	public boolean authenticate(int memberNumber, String encodedPassword)
			throws IllegalArgumentException {

		return true;
	}

	@Override
	public Collection<Member> getAllMembers() {
		return allMembers.values();
	}

	@Override
	public Collection<String> getAllMembersNames() {
		Collection<String> serializable = new ArrayList<String>(
				allMembers.size());
		for (String key : allMembers.keySet()) {
			serializable.add(key);
		}
		return serializable;
	}

	@Override
	public Member getMemberByName(String name) {
		return allMembers.get(name);
	}

	@Override
	public Member getMemberByNumber(int number) {
		// not very efficient, should be refactored.
		for (Member member : allMembers.values()) {
			if (member.getMemberNumber() == number) {
				return member;
			}
		}
		return null;
	}

	private Map<String, Member> allMembers = new HashMap<>();

}
