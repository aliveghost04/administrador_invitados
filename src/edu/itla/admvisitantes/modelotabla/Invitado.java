package edu.itla.admvisitantes.modelotabla;

public class Invitado {
	
	private int idInvitado;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String sexo;
	private boolean asistencia;
	private String evento;

	public int getIdInvitado() {
		return idInvitado;
	}

	public void setIdInvitado(int idInvitado) {
		this.idInvitado = idInvitado;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public boolean isAsistencia() {
		return asistencia;
	}

	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Invitado(int idInvitado, String nombre, String apellido, String telefono,
			String direccion, String sexo, String evento, boolean asistencia) {
		this.idInvitado = idInvitado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.sexo = sexo;
		this.evento = evento;
		this.asistencia = asistencia;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
