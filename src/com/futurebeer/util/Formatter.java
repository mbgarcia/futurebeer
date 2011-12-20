package com.futurebeer.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public enum Formatter {
	INSTANCE;

	Locale locale = null;
	NumberFormat nf = null;
	NumberFormat cf = null;
	DateFormat dhf= null;
	
	Formatter(){
		locale = new Locale("pt","BR");
		nf = NumberFormat.getInstance(locale);
		cf = NumberFormat.getCurrencyInstance(locale);
		dhf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		nf.setMinimumFractionDigits(2);
		cf.setMinimumFractionDigits(2);
	}
	
	public String formataDecimal(double num){
		return nf.format(num);
	}	

	public String formataMoeda(double num){
		return cf.format(num);
	}

	public String formataDataHora(Date date) {
		return dhf.format(date);
	}	
}
