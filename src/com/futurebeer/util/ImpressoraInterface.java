package com.futurebeer.util;

import java.util.List;

import com.futurebeer.exception.BaseException;

public interface ImpressoraInterface {
	public void imprime(List<String> linhas) throws BaseException;	
}
