package edu.itla.admvisitantes.properties;

public class Configuracion {

	private String url;
	private String usuario;
	private String contrase�a;
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

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
