package com.futurebeer.dao.interfaces;

import java.util.List;

import com.futurebeer.dto.MesaDTO;
import com.futurebeer.entity.Mesa;
import com.futurebeer.exception.BaseException;

public interface IMesaDao {
	public Mesa findByNumero(String numero) throws BaseException;
	public List<MesaDTO> getMesas() throws BaseException;
	public List<MesaDTO> abrirMesa(MesaDTO mesa) throws BaseException;
	public List<MesaDTO> fecharMesa(MesaDTO mesa) throws BaseException;
	public void addMesaExtra(MesaDTO mesa) throws BaseException;
	public void excluirMesa(MesaDTO mesaDTO) throws BaseException;
}
