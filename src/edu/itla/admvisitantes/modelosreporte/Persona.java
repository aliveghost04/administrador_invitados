package edu.itla.admvisitantes.modelosreporte;

public class Persona{
	
	private String nombreEvento;
	private String numeroPersonas;
	
	
	public Persona(String nombreEvento, int numeroPersonas) {
		
		this.nombreEvento = nombreEvento;
		this.numeroPersonas = String.valueOf(numeroPersonas);
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}


	public String getNumeroPersonas() {
		return numeroPersonas;
	}


	public void setNumeroPersonas(String numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}
	
	
	

}
