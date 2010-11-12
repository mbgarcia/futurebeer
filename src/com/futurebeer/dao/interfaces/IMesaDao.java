package com.futurebeer.dao.interfaces;

import java.util.List;

import com.futurebeer.dto.MesaDTO;
import com.futurebeer.exception.BaseException;

public interface IMesaDao {
	public List<MesaDTO> getMesas() throws BaseException;
}
