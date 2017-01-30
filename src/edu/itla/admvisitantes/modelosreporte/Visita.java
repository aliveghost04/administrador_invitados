package edu.itla.admvisitantes.modelosreporte;

public class Visita {

	private String nombreEvento;
	private String numeroVisitas;
	
	
	public Visita(String nombreEvento, int numeroPersonas) {
		
		this.nombreEvento = nombreEvento;
		this.numeroVisitas = String.valueOf(numeroPersonas);
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}


	public String getNumeroPersonas() {
		return numeroVisitas;
	}


	public void setNumeroPersonas(String numeroPersonas) {
		this.numeroVisitas = numeroPersonas;
	}
	
	
	

}
