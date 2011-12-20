package com.futurebeer.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.futurebeer.dao.FactoryDao;
import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.dto.MesaDTO;
import com.futurebeer.dto.PedidoDTO;
import com.futurebeer.entity.Mesa;
import com.futurebeer.entity.MesaOcupacao;
import com.futurebeer.exception.BaseException;

/**
 * Classe utilizada para impressao.
 * 
 * @author marcelogarcia
 *
 */
public class SerialPrinterUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SerialPrinterUtil.class);

	private static SerialPrinterUtil instance;
		
	//TODO: dependency injection
	private Map<String, ImpressoraInterface> printers = new HashMap<String, ImpressoraInterface>();

	private final ResourceBundle configFile = ResourceBundle.getBundle("com.futurebeer.configImpressora");
		
	private SerialPrinterUtil() {}

	public static SerialPrinterUtil getInstance() {
		if (instance == null) {
			instance = new SerialPrinterUtil();
		}

		return instance;
	}
	
	private ImpressoraInterface getImpressora(String categoria){
		ImpressoraInterface impressora = printers.get(categoria);
		if (impressora == null){
			String[] config = configFile.getString(categoria).split(";");
			impressora = new Impressora(config[0], Integer.parseInt(config[1]), Boolean.parseBoolean(config[2]));
			printers.put(categoria, impressora);
		}
		return impressora;
	}
	
	public void imprimePedido(PedidoDTO pedidoDTO) throws BaseException{
		MesaOcupacao ocupacao = FactoryDao.getInstance().getMesaOcupacaoDao().findById(Integer.valueOf(pedidoDTO.getIdOcupacao()));
		Mesa mesa = ocupacao.getMesa();
		List<ItemPedidoDTO> listaItensDTO = pedidoDTO.getItens();
		Map<String, List<ItemPedidoDTO>> pedidosPorImpressora = new HashMap<String, List<ItemPedidoDTO>>(); 
		
		try {
			//separacao dos pedidos por tipo
			for (ItemPedidoDTO itemPedidoDTO : listaItensDTO) {
				List<ItemPedidoDTO> pedidos = pedidosPorImpressora.get(itemPedidoDTO.getTipoProduto().getDescricao());
				if (pedidos == null){
					pedidos = new ArrayList<ItemPedidoDTO>();
					pedidosPorImpressora.put(itemPedidoDTO.getTipoProduto().getDescricao(), pedidos);
				}
				pedidos.add(itemPedidoDTO);
			}
			
			enviaImpressao(pedidosPorImpressora, mesa);
		} catch (BaseException e) {
			logger.error(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_IMPRESSAO_PEDIDO) + e.getMessage(), e);
			throw new BaseException(e.getMessage() , e);
		}
	}
	
	private void enviaImpressao(Map<String, List<ItemPedidoDTO>> pedidosPorImpressora, Mesa mesa) throws BaseException{
		Set<String> tipos = pedidosPorImpressora.keySet();
		ImpressoraInterface serial = null;
		for (String tipo : tipos) {
			List<String> impressao = new ArrayList<String>();
			serial = this.getImpressora(tipo);

			impressao.add(StringUtils.center("----- Pedidos para mesa: " + mesa.getNumero() + " -----", 40));
			impressao.add(StringUtils.rightPad("Cod",5) + StringUtils.rightPad("Descricao", 25) + StringUtils.rightPad("Qtd", 5));
			
			List<ItemPedidoDTO> itens = pedidosPorImpressora.get(tipo);
			for (ItemPedidoDTO item : itens) {
				impressao.add(StringUtils.rightPad(String.valueOf(item.getIdProduto()), 5) 
						+ StringUtils.rightPad(item.getDescricao(), 25) 
						+ StringUtils.rightPad(String.valueOf(item.getQtdade()), 5));
			}
			impressao.add(StringUtils.center("----------------------------------------", 40));
			impressao.add("\n\n\n");
			serial.imprime(impressao);
		}
	}
	
	public void imprimirComanda(MesaDTO mesaDTO) throws BaseException{
		MesaOcupacao ocupacao = FactoryDao.getInstance().getMesaOcupacaoDao().findById(Integer.valueOf(mesaDTO.getIdOcupacao()));
		Mesa mesa = ocupacao.getMesa();
		List<ItemPedidoDTO> todosPedidos = FactoryDao.getInstance().getMesaOcupacaoDao().getPedidosMesa(mesaDTO.getIdOcupacao());
		ImpressoraInterface serial = this.getImpressora("Pedidos");
		
		List<String> impressao = new ArrayList<String>();
		
		try {
			impressao.add(StringUtils.center("Future Beer", 40));
			Date data =  Calendar.getInstance().getTime();
			SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			impressao.add(formatDate.format(data));
			impressao.add(StringUtils.center("Mesa Nº " + mesa.getNumero(), 40));
			impressao.add(StringUtils.rightPad("Qtd",4) + StringUtils.rightPad("Descricao", 20) + StringUtils.rightPad("Unt", 8) + StringUtils.rightPad("Total", 8));
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			double valorTotal = 0;
			
			for (ItemPedidoDTO item : todosPedidos) {
				impressao.add(StringUtils.rightPad(String.valueOf(item.getQtdade()), 4)
						+ StringUtils.rightPad(String.valueOf(item.getDescricao()), 20) 
						+ StringUtils.rightPad(item.getValorUnitarioFormatado(), 8) 
						+ StringUtils.rightPad(item.getValorTotalFormatado(), 8));
				valorTotal += item.getValorUnitario() * item.getQtdade();
			}
			
			impressao.add("Total: " + Formatter.INSTANCE.formataDecimal(valorTotal));
			impressao.add(StringUtils.center("----------------------------------------", 40));
			impressao.add("\n\n\n");
			serial.imprime(impressao);
		} catch (BaseException e) {
			logger.error(MessagesUtil.getInstance().getWebMessage(MessagesUtil.ERRO_IMPRESSAO_COMANDA) + e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}
}