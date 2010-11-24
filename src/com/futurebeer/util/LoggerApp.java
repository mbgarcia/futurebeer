package com.futurebeer.util;

import org.apache.log4j.Logger;

public class LoggerApp {
	/**
	* logger.
	*/
	private static Logger logger = Logger.getLogger(LoggerApp.class);

	/**
	* Construtor da Classe.
	*/
	public LoggerApp() {
	}

	/**
	* Log de debug.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @since 1.0
	*/
	public static void debug(Object objMessage) {
	logger.debug(objMessage);
	}

	/**
	* Log de info.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @since 1.0
	*/
	public static void info(Object objMessage) {
	logger.info(objMessage);
	}

	/**
	* Log de warning.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @since 1.0
	*/
	public static void warn(Object objMessage) {
	logger.warn(objMessage);
	}

	/**
	* Log de debug.
	* <p>
	*
	* @param objMessage Mensagem de erro
	* @since 1.0
	*/
	public static void error(Object objMessage) {
	logger.error(objMessage);
	}

	/**
	* Log fatal.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @since 1.0
	*/
	public static void fatal(Object objMessage) {
	logger.debug(objMessage);
	}

	/**
	* Log de warn.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void warn(Object objMessage, Throwable e) {
	logger.debug(objMessage, e);
	}

	/**
	* Log de error.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void error(Object objMessage, Throwable e) {
	logger.error(objMessage, e);
	}

	/**
	* Log fatal.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void fatal(Object objMessage, Throwable e) {
	logger.fatal(objMessage, e);
	}

	/**
	* Log de warn.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void warn(Object objMessage, Exception e) {
	logger.debug(objMessage, e);
	}

	/**
	* Log de error.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void error(Object objMessage, Exception e) {
	logger.error(objMessage, e);
	}

	/**
	* Log fatal.
	* <p>
	*
	* @param objMessage Mensagem de log
	* @param e Exception
	* @since 1.0
	*/
	public static void fatal(Object objMessage, Exception e) {
	logger.fatal(objMessage, e);
	}

}
