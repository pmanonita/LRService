package com.lr.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionManager {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml 
			Configuration configuration = new Configuration().configure("/com/lr/db/hibernate.cfg.xml");												
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
			applySettings(configuration.getProperties());
			SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
			
			return sessionFactory;			
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.out.println("SessionFactory creation failed." + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}
	

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
