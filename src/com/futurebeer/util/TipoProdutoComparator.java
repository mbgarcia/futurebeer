package com.futurebeer.util;

import java.util.Comparator;

import com.futurebeer.dto.ProdutoDTO;

public class TipoProdutoComparator implements Comparator<ProdutoDTO>{
	
	public int compare(ProdutoDTO a, ProdutoDTO b){
		return a.getTipo().compareTo(b.getTipo());
	}

}
