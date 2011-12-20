package com.futurebeer.testes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * Esta classe controla a impressao apenas para portas seriais.
 * 
 * @author marcelo.garcia
 */
public class TesteImpressora {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TesteImpressora.class);
	
	private String commName = null;
	private CommPortIdentifier serialId = null;
	private SerialPort serialPort;

	/**
	 * Metodo construtor que recebe um objeto que identifica a porta serial.
	 */
	public TesteImpressora(String commId) {
		this.commName = commId;
    }
	
	private boolean constroiAcessoSerial() throws Exception{
		boolean acessoOk = false;

		try {
			serialId = CommPortIdentifier.getPortIdentifier(commName);
			serialPort = (SerialPort) serialId.open(TesteImpressora.class.getName(), 2000);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
			serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			acessoOk = true;
		} catch (Exception e) {
			logger.error("Erro ao criar objeto de acesso à porta serial: " + commName, e);
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
	
	public void imprime(List<String> linhas, boolean corte) throws Exception{
		
		OutputStream outputStream = null;
    	try {
    		constroiAcessoSerial();
    		
    		outputStream = serialPort.getOutputStream();
    		
    		for (String linha : linhas) {
    			linha = "\n" + linha + "\n";
    			outputStream.write(linha.getBytes());
			}
    		outputStream.flush();
    	} catch (Exception e) {
    		logger.error("Erro de impressão.", e);
    		throw new Exception("Erro de impressão.", e);
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
	
	public void imprime(byte[] linha) throws Exception{
		
		OutputStream outputStream = null;
    	try {
    		constroiAcessoSerial();
    		
    		outputStream = serialPort.getOutputStream();    		
			outputStream.write(linha);
    		outputStream.flush();
    	} catch (Exception e) {
    		logger.error("Erro de impressão.", e);
    		throw new Exception("Erro de impressão.", e);
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
	
	public static void main(String[] args) {
		TesteImpressora t = new TesteImpressora("COM1");
		try {
//			t.imprime("\nMarcelo de Brito Garcia\n\n".getBytes());
			
			List<String> impressao = new ArrayList<String>();	
			impressao.add(StringUtils.center("Future Beer", 40));			
			impressao.add(StringUtils.center("Marcelo de Brito Garcia", 40));			
//			t.imprime(impressao, true);
			

			Hex hex = new Hex();
			byte cmdGuilhotina[] = {0x1B,0x69};
			
			byte[] b = hex.encode("0x11".getBytes());
			
			System.out.println(b);
			
			
			
/*
			String linha = hex.encodeHexString("0x11h".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x11".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x15h".getBytes());
			t.imprime(linha.getBytes());
			
			linha = hex.encodeHexString("0x15".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1B 0x6D".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1B 0x77".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1Bh 0x77h".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1B".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x77".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1Bh".getBytes());
			t.imprime(linha.getBytes());
			
			linha = hex.encodeHexString("0x77h".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1Bh 0x69h".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1B 0x69".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1B".getBytes());
			t.imprime(linha.getBytes());
			linha = hex.encodeHexString("0x69".getBytes());
			t.imprime(linha.getBytes());

			linha = hex.encodeHexString("0x1Bh".getBytes());
			t.imprime(linha.getBytes());
			linha = hex.encodeHexString("0x69h".getBytes());
			t.imprime(linha.getBytes());
*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
