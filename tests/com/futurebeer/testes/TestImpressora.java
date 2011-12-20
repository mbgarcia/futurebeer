package com.futurebeer.testes;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.futurebeer.util.Impressora;
import com.futurebeer.util.ImpressoraInterface;

public class TestImpressora {
	@Test
	public void testPortaExistente(){
		ImpressoraInterface impressora = new Impressora("COM1", 57600, true);
//		Assert.assertTrue(impressora.portaExistente());
	}

	@Test
	public void testPortaInexistente(){
		ImpressoraInterface impressora = new Impressora("COM9", 9600, false);
//		Assert.assertFalse(impressora.portaExistente());
	}

	@Test
	public void testImpressoraAtiva(){
		ImpressoraInterface impressora = new Impressora("COM6", 9600, false);
//		Assert.assertTrue(impressora.isAtiva());
	}

	@Test
	public void testImpressoraDesligada(){
		ImpressoraInterface impressora = new Impressora("COM5", 9600, false);
//		Assert.assertFalse(impressora.isAtiva());
	}
	
	public static void main(String[] args) {
		try {
			
			//tentativa para diebold
//			FileWriter fileWriter = new FileWriter("COM1");
//			fileWriter.write("Imprime Diebold");
//			fileWriter.write("\n\n");
//			fileWriter.close();
			//nao funcionou
			
			FileOutputStream fos = new FileOutputStream("COM5");
//			PrintStream ps = new PrintStream(fos);
//			String s = new String("Marcelo Garcia\n\n\n".getBytes());
//			ps.print(s);
//			Impressora controlA = new Impressora("COM1");
//			controlA.imprimeLinha("\n\n\n002  Teste de pedido02     02\n\n\n");
			
			Impressora controlB = new Impressora("COM1", 57600, false);
			List<String> linhas = new ArrayList<String>();
			linhas.add("001  Teste de pedido01     06");
			linhas.add("002  Teste de pedido02     02");
			linhas.add("<ESC> 'i'");
			linhas.add("<ESC> 'm'");
			controlB.imprime(linhas);
			

//			Impressora controlC = new Impressora("COM9");
//			controlC.imprimeLinha("001  Teste de pedido01     06");
//			controlC.imprimeLinha("002  Teste de pedido02     02");

			System.out.println("COM1 ");
			System.out.println("COM5 ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
