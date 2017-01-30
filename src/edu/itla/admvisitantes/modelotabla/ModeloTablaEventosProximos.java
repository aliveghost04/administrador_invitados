package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaEventosProximos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnaTabla = {"Nombre"};
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	private ResultSet rs;
	
	public ModeloTablaEventosProximos() {
		try {
			rs = Conexion.getInstancia().hacerConsulta("SELECT id_evento, nombre_evento, "
					+ "ubicacion_evento, fecha_evento, hora_evento FROM eventos "
					+ "WHERE fecha_evento BETWEEN DATE_ADD(CURDATE(), INTERVAL 1 DAY) "
					+ "AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH)");
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id_evento"), rs.getString("nombre_evento"), 
						rs.getString("fecha_evento") + " " + rs.getString("hora_evento"), 
						rs.getString("ubicacion_evento")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] informacion(int fila) {
		
		Evento evento = eventos.get(fila);
		
		String[] retorno = new String[3];
		retorno[0] = "Nombre: " + evento.getNombre();
		retorno[1] = "Fecha: " + evento.getFecha();
		retorno[2] = "Ubicación: " + evento.getUbicacion();
		return retorno;
		
	}
	
	public int getColumnCount() {
		return columnaTabla.length;
	}

	public int getRowCount() {
		return eventos.size();
	}

	public Object getValueAt(int fila, int columna) {
		
		Evento evento = eventos.get(fila);
		
		String retorno = "";
		
		switch (columna) {
		case (0):
			retorno = evento.getNombre();
		break;
		}
		
		return retorno;
	}
	
	public String getColumnName(int columna) {
		return columnaTabla[columna];
	}
	
}
