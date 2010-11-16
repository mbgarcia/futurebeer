package com.futurebeer.dao;

import java.util.List;

import com.futurebeer.dao.interfaces.IMesaOcupacaoDao;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.exception.BaseException;

public class MesaOcupacaoDao implements IMesaOcupacaoDao{

	@Override
	public List<MesaDTO> getOcupacoes() throws BaseException {
		return null;
	}

	@Override
	public MesaOcupacao findById(int id) throws BaseException {
		return null;
	}
}
