package com.futurebeer.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static HibernateUtil instance;
    
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private HibernateUtil() {	}
    
    public static HibernateUtil getInstance(){
    	if (instance == null){
    		instance = new HibernateUtil();
    	}
    	
    	return instance;
    }

    private static SessionFactory buildSessionFactory() {
    	System.out.println("buildSessionFactory");
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public Session getSession(){
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();
    	return session;
    }
}