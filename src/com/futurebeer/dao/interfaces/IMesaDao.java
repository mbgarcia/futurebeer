package com.futurebeer.dao.interfaces;

import java.util.List;

import com.futurebeer.dto.MesaDTO;
import com.futurebeer.exception.BaseException;

public interface IMesaDao {
	public List<MesaDTO> getMesas() throws BaseException;
	public void abrirMesa(MesaDTO mesa) throws BaseException;
	public void fecharMesa(MesaDTO mesa) throws BaseException;
}
