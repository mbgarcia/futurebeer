package com.futurebeer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.swing.filechooser.FileSystemView;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import com.futurebeer.dto.ItemPedidoDTO;
import com.futurebeer.exception.BaseException;

public class JasperUtil {

	private static JasperUtil instance;

	private JasperUtil() {
	}

	public static JasperUtil getInstance() {
		if (instance == null) {
			instance = new JasperUtil();
		}

		return instance;
	}

	public void gerarRelatorio(FacesContext context, List<ItemPedidoDTO> dados,	String numMesa) throws BaseException{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("P_MESA", numMesa);

		Map<TipoProduto, List<ItemPedidoDTO>> pedidosSeparados = new HashMap<TipoProduto, List<ItemPedidoDTO>>();
		for (ItemPedidoDTO itemPedidoDTO : dados) {
			List<ItemPedidoDTO> itens = pedidosSeparados.get(itemPedidoDTO.getTipoProduto());
			if (itens == null) {
				itens = new ArrayList<ItemPedidoDTO>();
				pedidosSeparados.put(itemPedidoDTO.getTipoProduto(), itens);
			}
			itens.add(itemPedidoDTO);
		}

		Calendar calendar = Calendar.getInstance();
		Locale br = new Locale("pt", "BR");
		DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, br);
		String data = format.format(calendar.getTime()).replaceAll("/", "_");

		Set<TipoProduto> tipos = pedidosSeparados.keySet();
		for (TipoProduto tipoProduto : tipos) {
			InputStream stream = null;
			OutputStream out = null;
			try {
				stream = context.getExternalContext().getResourceAsStream("resources/report/pedido.jasper");
				List<ItemPedidoDTO> pedidosImpressao = pedidosSeparados.get(tipoProduto);
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pedidosImpressao);
				parametros.put("P_TIPO_PEDIDO", tipoProduto.getDescricao());

				JasperPrint jasperPrint = JasperFillManager.fillReport(stream, parametros, dataSource);

				File dir = FileSystemView.getFileSystemView().getRoots()[0];
				String path = dir.getAbsolutePath();
				path += "futurebeer/files/";
				System.out.println(path);
				dir = new File(path);
				dir.mkdirs();


				calendar.setTime(new Date());
				File dest = new File(dir, "pedido_" + numMesa + "_"	+ data + "_" + calendar.getTimeInMillis() + ".pdf");
				dest.createNewFile();
				out = new FileOutputStream(dest);
				out.flush();
				pdfExporter(jasperPrint, out);
			} catch (Exception e) {
				throw new BaseException("Erro ao gerar relatorio do pedido.", e);
			}
			finally{
				try {
					if (stream != null){
						stream.close();
					}
					if (out != null){
						out.close();
					}
				} catch (IOException e) {
					throw new BaseException("Erro ao fechar stream.", e);
				}				
			}
		}
	}

	public void gerarRelatorio(List<ItemPedidoDTO> dados, String numMesa) throws BaseException{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("P_MESA", numMesa);
		
		Map<TipoProduto, List<ItemPedidoDTO>> pedidosSeparados = new HashMap<TipoProduto, List<ItemPedidoDTO>>();
		for (ItemPedidoDTO itemPedidoDTO : dados) {
			List<ItemPedidoDTO> itens = pedidosSeparados.get(itemPedidoDTO.getTipoProduto());
			if (itens == null) {
				itens = new ArrayList<ItemPedidoDTO>();
				pedidosSeparados.put(itemPedidoDTO.getTipoProduto(), itens);
			}
			itens.add(itemPedidoDTO);
		}
		
		Calendar calendar = Calendar.getInstance();
		Locale br = new Locale("pt", "BR");
		DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, br);
		String data = format.format(calendar.getTime()).replaceAll("/", "_");
		
		Set<TipoProduto> tipos = pedidosSeparados.keySet();
		for (TipoProduto tipoProduto : tipos) {
			InputStream stream = null;
			OutputStream out = null;
			JRBeanCollectionDataSource dataSource = null;
			try {
				File file = new File("/desenvolvimento/projetos/futurebeer/futurebeer/WebContent/resources/report/pedido.jasper");
				stream = new FileInputStream(file);
				List<ItemPedidoDTO> pedidosImpressao = pedidosSeparados.get(tipoProduto);
				dataSource = new JRBeanCollectionDataSource(pedidosImpressao);
				parametros.put("P_TIPO_PEDIDO", tipoProduto.getDescricao());
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(stream, parametros, dataSource);
				
				calendar.setTime(new Date());
				
				File dir = FileSystemView.getFileSystemView().getRoots()[0];
				String path = dir.getAbsolutePath();
				path += "futurebeer/files/";
				System.out.println(path);
				dir = new File(path);
				dir.mkdirs();

				File dest = new File(dir, "pedido_" + numMesa + "_"	+ data + "_" + calendar.getTimeInMillis() + ".pdf");
				dest.createNewFile();
				out = new FileOutputStream(dest);
				out.flush();
				pdfExporter(jasperPrint, out);
			} catch (Exception e) {
				throw new BaseException("Erro ao gerar relatorio do pedido.", e);
			}
			finally{
				try {
					if (stream != null){
						stream.close();
					}
					if (out != null){
						out.close();
					}
				} catch (IOException e) {
					throw new BaseException("Erro ao fechar stream.", e);
				}				
			}
		}
	}

	private void pdfExporter(JasperPrint jasperPrint, OutputStream out) throws BaseException{
		try {
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
			exporter.reset();
		} catch (Exception e) {
			throw new BaseException("Erro ao gerar PDF do relatorio" , e);
		}
	}
	
	public static void main(String[] args) {
		List<ItemPedidoDTO> itens = new ArrayList<ItemPedidoDTO>();
		ItemPedidoDTO item = new ItemPedidoDTO();
		item.setDescricao("Churrasco");
		item.setTipoProduto(TipoProduto.ALIMENTO);
		item.setQtdade(2);
		itens.add(item);
		item = new ItemPedidoDTO();
		item.setDescricao("Frango");
		item.setTipoProduto(TipoProduto.ALIMENTO);
		item.setQtdade(4);
		itens.add(item);
		item = new ItemPedidoDTO();
		item.setDescricao("Agua");
		item.setTipoProduto(TipoProduto.BEBIDA);
		item.setQtdade(4);
		itens.add(item);
		item = new ItemPedidoDTO();
		item.setDescricao("Acucar");
		item.setTipoProduto(TipoProduto.COZINHA);
		item.setQtdade(4);
		itens.add(item);
		
		try {
			new JasperUtil().gerarRelatorio(itens, "50");
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
