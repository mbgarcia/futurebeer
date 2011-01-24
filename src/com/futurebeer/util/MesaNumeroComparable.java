package com.futurebeer.util;

import java.util.Comparator;

import com.futurebeer.dto.MesaDTO;

public class MesaNumeroComparable implements Comparator<MesaDTO>{

	public int compare(MesaDTO m1, MesaDTO m2) {
		return m1.getNumero().compareTo(m2.getNumero());
	}

}
