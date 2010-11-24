package com.futurebeer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.futurebeer.entity.PersistenceManager;

public class PersistenceAppListener implements ServletContextListener {
	 
	  public void contextInitialized(ServletContextEvent evt) {
	  }

	  public void contextDestroyed(ServletContextEvent evt) {
	  
	    PersistenceManager.getInstance().closeEntityManagerFactory();
	  }
	}