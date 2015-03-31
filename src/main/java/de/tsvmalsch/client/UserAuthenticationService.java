package de.tsvmalsch.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.model.Member;
 
@RemoteServiceRelativePath("auth")
public interface UserAuthenticationService extends RemoteService {
	
	Collection<Member> getAllMembers ();
	
	boolean authenticate(int memberNumber, String encodedPassword)
			throws IllegalArgumentException;
}
