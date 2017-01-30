package edu.itla.admvisitantes.modelotabla;

public class Evento {

	private int idEvento;
	private String nombre;
	private String fecha;
	private String ubicacion;

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Evento(int idEvento, String nombre, String fecha, String ubicacion) {
		this.idEvento = idEvento;
		this.nombre = nombre;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
}
