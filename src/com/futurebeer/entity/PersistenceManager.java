package com.futurebeer.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

	public static final boolean DEBUG = true;

	private static final PersistenceManager singleton = new PersistenceManager();

	protected EntityManagerFactory emf;

	public static PersistenceManager getInstance() {
		return singleton;
	}

	private PersistenceManager() {
		System.out.println("PersistenceManager construtor");		
	}

	public EntityManagerFactory getEntityManagerFactory() {
		if (emf == null)
			createEntityManagerFactory();
		return emf;
	}

	public void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			if (DEBUG)
				System.out.println("n*** Persistence finished at "	+ new java.util.Date());
		}
	}

	protected void createEntityManagerFactory() {
		try {
			System.out.println("createEntityManagerFactory");
			this.emf = Persistence.createEntityManagerFactory("futurebeer");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		if (DEBUG)
			System.out.println("n*** Persistence started at " + new java.util.Date());
	}
}