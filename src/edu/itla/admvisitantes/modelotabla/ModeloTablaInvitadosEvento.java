package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaInvitadosEvento extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnasTabla = {"Nombre", "Apellido", "Telefono", "Dirección", "Sexo", "Asistencia"};
	private static ArrayList<Invitado> invitados;
	private int idInvitado;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String sexo;
	private String evento;
	private boolean asistencia;
	private Object[] fila = new Object[6];
	
	public ModeloTablaInvitadosEvento(int idEvento) {
		
		invitados = new ArrayList<Invitado>();
		try {
			ResultSet rs = Conexion.getInstancia().hacerConsulta("SELECT i.id_invitado, i.nombre_invitado,"
					+ "i.apellido_invitado, i.telefono_invitado, i.direccion_invitado, i.sexo_invitado,"
					+ "i.asistencia_invitado, e.nombre_evento FROM invitados i JOIN eventos e USING (id_evento)"
					+ "WHERE id_evento = " + idEvento);
			while (rs.next()) {
				idInvitado = rs.getInt("id_invitado");
				nombre = rs.getString("nombre_invitado");
				apellido = rs.getString("apellido_invitado");
				telefono = rs.getString("telefono_invitado");
				direccion = rs.getString("direccion_invitado");
				sexo = rs.getString("sexo_invitado");
				evento = rs.getString("nombre_evento");
				asistencia = rs.getBoolean("asistencia_invitado");
				invitados.add(new Invitado(idInvitado, nombre, apellido, telefono, direccion, sexo, evento, asistencia));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cargarTabla();
	}
	
	public static int getIdInvitado(int fila) {
		return invitados.get(fila).getIdInvitado();
	}
	
	public void cargarTabla() {
		
		for (int x = 0; x < columnasTabla.length; x++) {
			addColumn(columnasTabla[x]);
		}
		
		for (int x = 0; x < invitados.size(); x++) {
			Invitado invitado = invitados.get(x);
			
			fila[0] = invitado.getNombre();
			fila[1] = invitado.getApellido();
			fila[2] = invitado.getTelefono();
			fila[3] = invitado.getDireccion();
			fila[4] = invitado.getSexo();
			fila[5] = invitado.isAsistencia();
			addRow(fila);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columna) {
		
		switch (columna) {
		case (0):
			return String.class;
		case (1):
			return String.class;
		case (2):
			return String.class;
		case (3):
			return String.class;
		case (4):
			return String.class;
		default:
			return Boolean.class;
		}
	}
	
	public int getIdEvento(int fila) {
		return invitados.get(fila).getIdInvitado();
	}
}
