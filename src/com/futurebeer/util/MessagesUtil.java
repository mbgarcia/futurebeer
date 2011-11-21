package com.futurebeer.util;

import java.util.ResourceBundle;

public class MessagesUtil {
	private ResourceBundle webMessages = ResourceBundle.getBundle("com.futurebeer.web_messages");
	
	public static String AVISO_EXISTE_MESA_COM_NUMERO = "aviso_existe_mesa_com_numero";
	public static String AVISO_EXISTE_MESA_EXTRA = "aviso_existe_mesa_extra";
	public static String AVISO_EXISTE_PRODUTO_COM_DESC = "aviso_existe_produto_com_desc";
	public static String AVISO_EXISTE_PRODUTO_COM_CODIGO = "aviso_existe_produto_com_codigo";
	public static String AVISO_EXISTE_PRODUTO_COM_DESC_INATIVO = "aviso_existe_produto_com_desc_inativo";
	public static String AVISO_EXISTE_PRODUTO_COM_CODIGO_INATIVO = "aviso_existe_produto_com_codigo_inativo";
	public static String AVISO_MESA_OCUPADA = "aviso_mesa_ocupada";

	public static String SUCESSO_SALVAR_MESA = "sucesso_salvar_mesa";
	public static String SUCESSO_SALVAR_PROD = "sucesso_salvar_produto";
	public static String SUCESSO_DEL_MESA = "sucesso_del_mesa";
	public static String SUCESSO_DEL_PROD = "sucesso_del_produto";
	public static String SUCESSO_SALVAR_PEDIDO = "sucesso_salvar_pedido";
	public static String SUCESSO_EXCLUIR_ITEM = "sucesso_excluir_item";
	

	public static String ERRO_SALVAR_MESA = "erro_salvar_mesa";
	public static String ERRO_SALVAR_PROD = "erro_salvar_produto";
	public static String ERRO_SALVAR_PEDIDO = "erro_salvar_pedido";
	public static String ERRO_PEDIDO_VAZIO = "erro_pedido_vazio";
	public static String ERRO_ID_PRODUTO_NULL = "erro_id_produto_null";
	public static String ERRO_DEL_PROD = "erro_del_produto";
	public static String ERRO_DEL_MESA = "erro_del_mesa";
	public static String ERRO_LIST_PROD = "erro_list_produto";
	public static String ERRO_FIND_PROD_ID = "erro_find_produto_id";
	public static String ERRO_FIND_PROD_CODIGO = "erro_find_produto_codigo";
	public static String ERRO_FIND_MESA_NUMERO = "erro_find_mesa_numero";
	public static String ERRO_FIND_PROD_DESC = "erro_find_produto_desc";
	public static String ERRO_EXCLUIR_ITEM = "erro_excluir_item";
	public static String ERRO_IMPRESSAO = "erro_impressao";
	public static String ERRO_IMPRESSAO_COMANDA = "erro_impressao_comanda";
	public static String ERRO_IMPRESSAO_PEDIDO = "erro_impressao_pedido";
	
	private static MessagesUtil instance = null;
	
	private MessagesUtil(){}
	
	public static MessagesUtil getInstance(){
		if (instance == null){
			instance = new MessagesUtil();
		}
		return instance;
	}
	
	public String getWebMessage(String key){
		return webMessages.getString(key);
	}
}
