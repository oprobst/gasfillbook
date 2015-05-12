package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.ConfigurationService;
import de.tsvmalsch.shared.model.Configuration;

/**
 * Implementation of the Configuration Service.
 *
 * @author Oliver Probst
 */
@SuppressWarnings("serial")
public class ConfigurationServiceImpl extends RemoteServiceServlet implements
		ConfigurationService {

	private Logger log = LoggerFactory
			.getLogger(ConfigurationServiceImpl.class);

	private Configuration currConfig = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tsvmalsch.client.ConfigurationService#getCurrentConfiguration()
	 */
	@Override
	public Configuration getCurrentConfiguration() {
		if (currConfig == null) {
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();
			session.beginTransaction();
			Query query = session
					.createQuery("FROM de.tsvmalsch.shared.model.Configuration ");

			List<Configuration> config = new ArrayList<Configuration>(
					query.list());

			session.getTransaction().commit();
			this.currConfig = config.get(config.size() - 1);

		}
		return this.currConfig;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tsvmalsch.client.ConfigurationService#storeConfiguration(de.tsvmalsch
	 * .shared.model.Configuration)
	 */
	@Override
	public Configuration storeConfiguration(Configuration config) {

		try {
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();
			session.beginTransaction();
			session.save(config);
			session.getTransaction().commit();
			return config;
		} catch (Exception e) {
			log.error("Could not store Configuration ", e);
			throw e;
		}

	}

}
