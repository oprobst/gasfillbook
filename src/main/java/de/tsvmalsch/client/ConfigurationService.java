package de.tsvmalsch.client;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.server.UserServiceImpl;
import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Member;

/**
 * Provide the currently active configuration
 * 
 * @author Oliver Probst
 */
@RemoteServiceRelativePath("config")
public interface ConfigurationService extends RemoteService {

	/**
	 * @return The most current configuration.
	 */
	Configuration getCurrentConfiguration();

	/**
	 * Store a modified configuration to the database and make it available to
	 * all users on the next {@link #getCurrentConfiguration()} call.
	 * 
	 * @param config
	 *            the configuration to store
	 */
	void storeConfiguration(Configuration config);
 
}
