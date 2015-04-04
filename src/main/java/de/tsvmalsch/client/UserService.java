package de.tsvmalsch.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.model.Member;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	Collection<Member> getAllMembers();

	boolean authenticate(int memberNumber, String encodedPassword)
			throws IllegalArgumentException;

	Member getMemberByName(String name);

	Collection<String> getAllMembersNames();

	Member getMemberByNumber(int number);

	Member getCurrentMember();

	Member getMemberToFillFor();

	/**
	 * The user which shall be charged for the next filling.
	 * 
	 * @param number
	 *            the member number. If -1 is provided, the club will be
	 *            charged. On -2 The current logged in member will be charged.
	 * @return
	 */
	void setMemberToFillFor(int number);
}
