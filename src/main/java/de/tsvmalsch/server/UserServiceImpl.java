package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.UserService;
import de.tsvmalsch.shared.model.Member;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	public UserServiceImpl() throws Exception {

		Collection<Member> moreDummys = CreateDemoDataService
				.createDummyMembers();
		for (Member dmem : moreDummys) {
			allMembers
					.put(dmem.getFirstName() + " " + dmem.getLastName(), dmem);
		}

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

		// TODO Authenticate

		// create session and store user
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(true);
		Member m = this.getMemberByNumber(memberNumber);
		session.setAttribute("currentMember", m);

		return true;
	}

	@Override
	public Member getCurrentMember() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("currentMember") == null)
			return null;

		Member m = (Member) session.getAttribute("currentMember");
		return m;

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
			if (!key.equals("TSV Malsch")) {
				serializable.add(key);
			}
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

	@Override
	public Member setMemberToFillFor(int memberNumber)
			throws IllegalArgumentException {

		// create session and store memberToFill
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(true);
		Member m = this.getMemberByNumber(memberNumber);
		session.setAttribute("memberToFillFor", m);
		return m;
	}

	@Override
	public Member getMemberToFillFor() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("memberToFillFor") == null)
			return null;

		Member m = (Member) session.getAttribute("memberToFillFor");
		return m;

	}

	private Map<String, Member> allMembers = new HashMap<>();

}
