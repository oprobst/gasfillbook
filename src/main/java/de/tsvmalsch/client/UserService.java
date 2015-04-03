package de.tsvmalsch.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.model.Member;

@RemoteServiceRelativePath("auth")
public interface UserService extends RemoteService {

	Collection<Member> getAllMembers();

	boolean authenticate(int memberNumber, String encodedPassword)
			throws IllegalArgumentException;

	Member getMemberByName(String name);

	Collection<String> getAllMembersNames();

	Member getMemberByNumber(int number);
}
