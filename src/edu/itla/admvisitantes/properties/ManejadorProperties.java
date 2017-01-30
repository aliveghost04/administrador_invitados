package edu.itla.admvisitantes.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ManejadorProperties {
	
	Properties properties = new Properties();
	
	public Object cargar() {
		File archivo = new File("config.cfg");
		if (archivo.exists()) {
			leerArchivo();
		} else {
			JOptionPane.showMessageDialog(null, "Falta el archivo de configuración 'config.cfg'",
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return new ManejadorProperties();
	}
	
	public void leerArchivo() {
		
		try {
			FileInputStream fis = new FileInputStream("config.cfg");
			properties.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Configuracion pro = Configuracion.getInstancia();
		
		pro.setDriver(properties.getProperty("driver"));
		pro.setUrl(properties.getProperty("url"));
		pro.setUsuario(properties.getProperty("usuario"));
		pro.setContraseña(properties.getProperty("password"));
	}
	
}
