package edu.itla.admvisitantes.modelotabla;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaEventos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaEventos instancia;
	private String[] columnasTabla = {"ID Evento", "Nombre", "Fecha", "Ubicación"};
	private static String consulta; 
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	
	public static ModeloTablaEventos getInstancia() {
		
		consulta = "SELECT id_evento, nombre_evento, fecha_evento, ubicacion_evento, hora_evento FROM eventos";
		if (instancia == null) {
			instancia = new ModeloTablaEventos(consulta);
		}
		return instancia;
	}
	
	public void agregar(Evento evento) {
		eventos.add(evento);
		fireTableDataChanged();
	}
	
	public void eliminar(int x){
		try {
			Conexion.getInstancia().eliminar(eventos.get(x));
		} catch (Exception e) {
			e.printStackTrace();
		}
		eventos.remove(x);
		fireTableDataChanged();
	}
	
	public void modificar(int x,Evento evento){
		eventos.set(x,evento);
		fireTableDataChanged();
	}
	
	private ModeloTablaEventos(String consulta) {
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
	
	public int getIdEvento(int fila) {
		int idEvento = eventos.get(fila).getIdEvento();
		return idEvento;
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
				valorCelda = String.valueOf(evento.getIdEvento());
			break;
			case (1):
				valorCelda = evento.getNombre();
			break;
			case (2):
				valorCelda = evento.getFecha();
			break;
			case (3):
				valorCelda = evento.getUbicacion();
			break;
		}
		
		return valorCelda;
	}
	
	public String getColumnName(int columnas) {
		return columnasTabla[columnas];
	}

}
