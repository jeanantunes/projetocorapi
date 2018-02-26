package br.com.odontoprev.portal.corretor.util;

import java.util.ResourceBundle;

public class PropertiesUtils {
	
	private static final ResourceBundle PROPERTY;
	
	public static final String PATH_XLS_EMPRESA = "server.path.empresa";
	
	public static final String PATH_XLS_VIDAS = "server.path.vidas";
	
	static {
		PROPERTY = ResourceBundle.getBundle("application");
	}

	private PropertiesUtils() {}
	
	public static String getProperty(final String nome){
		return PROPERTY.getString(nome);
	}
	
}
