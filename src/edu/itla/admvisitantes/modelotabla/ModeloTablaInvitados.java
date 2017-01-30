package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaInvitados extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaInvitados instancia;
	private String[] columnasTabla = {"Nombre", "Apellido", "Teléfono", "Dirección", "Sexo", "Evento"};
	private ArrayList<Invitado> invitados = new ArrayList<Invitado>();
	
	public static ModeloTablaInvitados getInstancia() {
		
		if (instancia == null) {
			instancia = new ModeloTablaInvitados();
		}
		return instancia;
	}
	
	public void agregar(Invitado invitado) {
		invitados.add(invitado);
		fireTableDataChanged();
	}
	
	public void eliminar(int fila){
		
		try {
			Conexion.getInstancia().eliminar(invitados.get(fila));
		} catch (Exception e) {
			e.printStackTrace();
		}
		invitados.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}
	
	public void modificar(int fila, Invitado invitado){
		invitados.set(fila, invitado);
		fireTableRowsUpdated(fila, fila);
	} 
	
	private ModeloTablaInvitados() {
		
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
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getColumnCount() {
		return columnasTabla.length;
	}

	public int getRowCount() {
		return invitados.size();
	}

	public Object getValueAt(int fila, int columna) {
		
		Invitado invitado = invitados.get(fila);
		
		String retorno = "";
		
		switch (columna) {
		case (0):
			retorno = invitado.getNombre();
		break;
		case (1):
			retorno = invitado.getApellido();
		break;
		case (2):
			retorno = invitado.getTelefono();
		break;
		case (3):
			retorno = invitado.getDireccion();
		break;
		case (4):
			retorno = invitado.getSexo();
		break;
		case (5):
			retorno = invitado.getEvento();
		break;
		}
		
		return retorno;
	}
	
	public String getColumnName(int columna) {
		return columnasTabla[columna];
	}
	
}
