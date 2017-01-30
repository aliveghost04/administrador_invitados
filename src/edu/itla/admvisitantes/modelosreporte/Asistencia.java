package edu.itla.admvisitantes.modelosreporte;

public class Asistencia {
	
	private String nombreEvento;
	private String porciento;
	
	public Asistencia(String nombreEvento, int porciento) {
		
		this.nombreEvento = nombreEvento;
		this.porciento = String.valueOf(porciento);
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getPorciento() {
		return porciento;
	}

	public void setPorciento(String porciento) {
		this.porciento = porciento;
	}
	
	

}
