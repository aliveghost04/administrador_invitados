package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaUsuarios extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaUsuarios instancia;
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	String[] columnasTabla = {"Nombre", "Apellido", "Usuario", "Contraseña", "Perfil de Usuario"};
	
	public static ModeloTablaUsuarios getInstancia() {
		
		if (instancia == null) {
			instancia = new ModeloTablaUsuarios();
		}
		return instancia;
	}
	
	private ModeloTablaUsuarios() {
		
		try {
			ResultSet resultados = Conexion.getInstancia().hacerConsulta("SELECT u.nombre_usuario,"
					+ " u.apellido_usuario, u.usuario, u.clave_usuario, "
					+ "c.nombre_cargo FROM usuarios u join cargos c using (id_cargo)");
			
			while (resultados.next()) {
				usuarios.add(new Usuario(resultados.getString("u.nombre_usuario"),
						resultados.getString("u.apellido_usuario"), resultados.getString("u.usuario"), 
						resultados.getString("u.clave_usuario"), resultados.getString("c.nombre_cargo")));
			}
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregar(Usuario usuario) {
		
		usuarios.add(usuario);
		fireTableDataChanged();
	}
	
	public void modificar(Usuario usuario, int x){
		
		usuarios.set(x , usuario);
		fireTableRowsUpdated(x, x);
		
	}
	public void eliminar(int fila){
		
		try {
			Conexion.getInstancia().eliminar(usuarios.get(fila));
		} catch (Exception e) {
			e.printStackTrace();
		}
		usuarios.remove(fila);
		fireTableRowsDeleted(fila, fila);
		
	}
	
	public int getColumnCount() {
		return columnasTabla.length;
	}

	public int getRowCount() {
		return usuarios.size();
	}

	public Object getValueAt(int fila, int columna) {
		
		Usuario usuario = usuarios.get(fila);
		
		String valorCelda = "";
		
		switch (columna) {
		case (0):
			valorCelda = usuario.getNombre();
		break;
		case (1):
			valorCelda = usuario.getApellido();
		break;
		case (2):
			valorCelda = usuario.getUsuario();
		break;
		case (3):
			valorCelda = usuario.getContrasena();
		break;
		case (4):
			valorCelda = String.valueOf(usuario.getPerfilUsuario());
		break;
		}
		return valorCelda;
	}
	
	public String getColumnName(int columnas) {
		return columnasTabla[columnas];
	}

}
