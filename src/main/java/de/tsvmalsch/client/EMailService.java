package de.tsvmalsch.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Check if the e-mail thread is started and restarts it if required.
 * 
 * @author Oliver Probst
 */
@RemoteServiceRelativePath("emailer")
public interface EMailService extends RemoteService {
	public void startEmailThread();

}
