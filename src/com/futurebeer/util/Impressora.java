package com.futurebeer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

import org.slf4j.Logger;

import com.futurebeer.exception.BaseException;

/**
 * Esta classe controla a impressao apenas para portas seriais.
 * 
 * @author marcelo.garcia
 */
public class Impressora  implements ImpressoraInterface{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Impressora.class);
	
	private String commName = null;
	private int baudrate;
	private boolean guilhotina;
	private CommPortIdentifier serialId = null;
	private SerialPort serialPort;

	/**
	 * Metodo construtor que recebe um objeto que identifica a porta serial.
	 */
	public Impressora(String commId, int baudrate, boolean guilhotina) {
		this.commName = commId;
		this.baudrate = baudrate;
		this.guilhotina = guilhotina;
    }
	
	private boolean constroiAcessoSerial() throws Exception{
		boolean acessoOk = false;

		try {
			serialId = CommPortIdentifier.getPortIdentifier(commName);
			serialPort = (SerialPort) serialId.open(Impressora.class.getName(), 2000);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
			serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			acessoOk = true;
		} catch (Exception e) {
			logger.error("Erro ao criar objeto de acesso a porta serial: " + commName, e);
		}

		if (!portaExistente()){
			logger.error("Porta " + commName +" inexistente.");
			throw new Exception("Porta " + commName +" inexistente.");
		}
		if (!isAtiva()){
			logger.error("Porta " + commName +" inativa ou impressora desligada.");
			throw new Exception("Porta " + commName +" inativa ou impressora desligada.");
		}

		return acessoOk;
	}
	
	public void imprime(List<String> linhas) throws BaseException{
		
		OutputStream outputStream = null;
    	try {
    		constroiAcessoSerial();
    		
    		outputStream = serialPort.getOutputStream();
    		
    		for (String linha : linhas) {
    			linha = "\n" + linha + "\n";
    			outputStream.write(linha.getBytes());
			}

    		if (guilhotina){
    			byte cmdGuilhotinaDiebold[] = {0x1B,0x69};
    			outputStream.write(cmdGuilhotinaDiebold);
    		}

    		outputStream.flush();
    	} catch (Exception e) {
    		logger.error(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_IMPRESSAO), e);
    		throw new BaseException(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_IMPRESSAO), e);
    	}finally{
    		try {
	    		if (outputStream != null){
	    			outputStream.close();
	    		}
	    		if (serialPort != null){
	    			serialPort.close();
	    			serialPort = null;
	    		}
    		} catch (IOException e) {
    			logger.error("Erro ao fechar conexao.", e);
    		}
    	}
    	
	}
	
	private boolean isAtiva() {
		
		if (!portaExistente())
			return false;
		return serialPort.isCTS();
	}

	private boolean portaExistente() {
		if (serialPort == null)
			return false;
		return true;
	}
}
