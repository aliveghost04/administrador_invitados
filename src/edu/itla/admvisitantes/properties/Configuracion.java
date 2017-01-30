package edu.itla.admvisitantes.properties;

public class Configuracion {

	private String url;
	private String usuario;
	private String contraseña;
	private String driver;
	private static Configuracion instancia;
	
	public static Configuracion getInstancia() {
		if (instancia == null) {
			instancia = new Configuracion();
		}
		return instancia;
	}
	
	private Configuracion() {
		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
