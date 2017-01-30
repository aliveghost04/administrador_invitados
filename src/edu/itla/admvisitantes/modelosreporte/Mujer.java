package edu.itla.admvisitantes.modelosreporte;

public class Mujer {

	private String nombreEvento;
	private String numero;
	
	
	public Mujer(String nombreEvento, int numero) {
		
		this.nombreEvento = nombreEvento;
		this.numero = String.valueOf(numero);
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}


	public String getNumeroPersonas() {
		return numero;
	}


	public void setNumeroPersonas(String numeroPersonas) {
		this.numero = numeroPersonas;
	}
	
	
	
}
