package edu.itla.admvisitantes.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.itla.admvisitantes.modelotabla.Evento;
import edu.itla.admvisitantes.modelotabla.Invitado;
import edu.itla.admvisitantes.modelotabla.Usuario;
import edu.itla.admvisitantes.properties.Configuracion;

public class Conexion {
	
	private Statement enunciado;
	private Connection conexion;
	private static Conexion instancia;
	private static Configuracion pro = Configuracion.getInstancia();
	
	public static Conexion getInstancia() throws Exception {
		
		if (instancia == null) {
			instancia = new Conexion();
		}
		return instancia;
	}
	
	private Conexion() throws Exception {
		
		Class.forName(pro.getDriver()).newInstance();
		conexion = DriverManager.getConnection(pro.getUrl(), pro.getUsuario(), pro.getContraseña());
		enunciado = conexion.createStatement();
	}
	
	public ResultSet hacerConsulta(String consulta) throws Exception {
		
		ResultSet resultados = null;
		
		resultados = enunciado.executeQuery(consulta);
		return resultados;
	}
	
	public void hacerCambio(String cambio) throws Exception {
		enunciado.execute(cambio);
	}
	
	public void eliminar(Evento evento){
		
		try {
			enunciado.execute("DELETE FROM eventos WHERE id_evento ='" + evento.getIdEvento()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminar(Usuario usuario){
		
		try {
			enunciado.execute("DELETE FROM usuarios WHERE usuario ='" + usuario.getUsuario()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminar(Invitado invitado){
		
		try {
			enunciado.execute("DELETE FROM invitados WHERE id_invitado ='" + invitado.getIdInvitado()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	