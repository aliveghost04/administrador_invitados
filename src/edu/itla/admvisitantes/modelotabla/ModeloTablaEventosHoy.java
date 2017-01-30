package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaEventosHoy extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private static ModeloTablaEventosHoy instancia;
	private String[] columnasTabla = {"Nombre", "Fecha", "Ubicación"};
	private static String consulta; 
	private static String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());;
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	
	public static ModeloTablaEventosHoy getInstancia() {
		
		consulta = "SELECT id_evento, nombre_evento, fecha_evento, ubicacion_evento, hora_evento FROM eventos where fecha_evento = '" + fechaHoy + "'";
		if (instancia == null) {
			instancia = new ModeloTablaEventosHoy(consulta);
		}
		return instancia;
	}
	
	public int verificar (int fila) {
		
		switch (fila) {
		case (-1):
			return fila;
		default:
			return eventos.get(fila).getIdEvento();
		}
	}
	
	private ModeloTablaEventosHoy(String consulta) {
		try {
			ResultSet resultados = Conexion.getInstancia().hacerConsulta(consulta);
			while (resultados.next()) {
				eventos.add(new Evento(resultados.getInt("id_evento"), resultados.getString("nombre_evento"),
						resultados.getString("fecha_evento") + " " + resultados.getString("hora_evento"), 
						resultados.getString("ubicacion_evento")));
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
		return eventos.size();
	}

	public Object getValueAt(int fila, int columna) {
		
		Evento evento = eventos.get(fila);
		
		String valorCelda = "";
		
		switch(columna) {
			case (0):
				valorCelda = evento.getNombre();
			break;
			case (1):
				valorCelda = evento.getFecha();
			break;
			case (2):
				valorCelda = evento.getUbicacion();
			break;
		}
		return valorCelda;
	}
	
	public String getColumnName(int columnas) {
		return columnasTabla[columnas];
	}

}