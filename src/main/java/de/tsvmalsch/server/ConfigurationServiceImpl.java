package de.tsvmalsch.server;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.ConfigurationService;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Member;

/**
 * Implementation of the Configuration Service.
 *
 * @author Oliver Probst
 */
@SuppressWarnings("serial")
public class ConfigurationServiceImpl extends RemoteServiceServlet implements
		ConfigurationService {

	Configuration currConfig = new Configuration();

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tsvmalsch.client.ConfigurationService#getCurrentConfiguration()
	 */
	@Override
	public Configuration getCurrentConfiguration() {
		return this.currConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tsvmalsch.client.ConfigurationService#storeConfiguration(de.tsvmalsch
	 * .shared.model.Configuration)
	 */
	@Override
	public void storeConfiguration(Configuration config) {
		this.currConfig = config;
		// TODO save to db.

	}

}
