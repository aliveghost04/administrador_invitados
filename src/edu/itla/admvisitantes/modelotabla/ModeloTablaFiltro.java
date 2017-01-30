package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaFiltro extends DefaultTableModel {

	private String[] columnasTabla = {"Nombre", "Apellido", "Teléfono", "Dirección", "Sexo", "Evento"};
	private ArrayList<Invitado> invitados = new ArrayList<Invitado>();
	private static final long serialVersionUID = 1L;
	private String[] fila = new String[columnasTabla.length];
	
	public ModeloTablaFiltro() {
		try {
			ResultSet resultado = Conexion.getInstancia().hacerConsulta("SELECT id_invitado, "
					+ "nombre_invitado, apellido_invitado, telefono_invitado, direccion_invitado,"
					+ " sexo_invitado, asistencia_invitado, nombre_evento FROM invitados NATURAL JOIN "
					+ "eventos ORDER BY nombre_invitado");
			while (resultado.next()) {
				invitados.add(new Invitado(resultado.getInt("id_invitado"), resultado.getString("nombre_invitado"), 
						resultado.getString("apellido_invitado"), resultado.getString("telefono_invitado"),
						resultado.getString("direccion_invitado"), resultado.getString("sexo_invitado"), resultado.getString("nombre_evento"),
						resultado.getBoolean("asistencia_invitado")));
			}
			cargarTabla();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			fila[5] = invitado.getEvento();
			addRow(fila);
		}
	}
	
}
