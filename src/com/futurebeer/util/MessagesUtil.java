package com.futurebeer.util;

import java.util.ResourceBundle;

public class MessagesUtil {
	private ResourceBundle webMessages = ResourceBundle.getBundle("web_messages");
	
	public static String AVISO_EXISTE_PRODUTO_COM_DESC = "aviso_existe_produto_com_desc";
	public static String AVISO_EXISTE_PRODUTO_COM_DESC_INATIVO = "aviso_existe_produto_com_desc_inativo";

	public static String SUCESSO_SALVAR_PROD = "sucesso_salvar_produto";
	public static String SUCESSO_DEL_PROD = "sucesso_del_produto";

	public static String ERRO_SALVAR_PROD = "erro_salvar_produto";
	public static String ERRO_ID_PRODUTO_NULL = "erro_id_produto_null";
	public static String ERRO_DEL_PROD = "erro_del_produto";
	public static String ERRO_LIST_PROD = "erro_list_produto";
	public static String ERRO_FIND_PROD_ID = "erro_find_produto_id";
	public static String ERRO_FIND_PROD_DESC = "erro_find_produto_desc";
	
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
