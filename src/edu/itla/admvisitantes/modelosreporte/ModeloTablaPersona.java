package edu.itla.admvisitantes.modelosreporte;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Evento;

public class ModeloTablaPersona extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaPersona instancia;
	private ArrayList<Persona> personas = new ArrayList<Persona>();;
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	String nombre;
	int cantidadPersonas;
	int cantidadEventos;
	ResultSet rs;
	int idEvento;

	String[] encabezados = { "  Nombre del Evento  ", " # Personas " };

	private ModeloTablaPersona() {

		try {
			 rs = Conexion.getInstancia().
					hacerConsulta("SELECT id_evento, nombre_evento, ubicacion_evento,"
							+ " fecha_evento, hora_evento FROM eventos");
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id_evento"), rs.getString("nombre_evento"),
						rs.getString("fecha_evento") + " " + rs.getString("hora_evento"), 
						rs.getString("ubicacion_evento")));
			}
			cantidadEventos = eventos.size();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		for (int x = 0; x < cantidadEventos ; x++) {
			try {
				idEvento = eventos.get(x).getIdEvento();
				rs = Conexion.getInstancia().hacerConsulta("SELECT e.nombre_evento, COUNT(i.id_invitado)"
						+ "FROM eventos e JOIN invitados i USING (id_evento) WHERE id_evento = " + idEvento +
						" AND asistencia_invitado = 1");
				if (rs.next()) {
					nombre = rs.getString("e.nombre_evento");
					if (nombre == null) {
						nombre = eventos.get(x).getNombre();
					}
					cantidadPersonas = rs.getInt(2);
					personas.add(new Persona(nombre, cantidadPersonas));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}	
	}

	public static ModeloTablaPersona getInstancia() {
		
		if (instancia == null) {
			instancia = new ModeloTablaPersona();
		}
		return instancia;
	}

	public String getColumnName(int x) {

		return encabezados[x];
	}

	public int getColumnCount() {
		return encabezados.length;
	}

	public int getRowCount() {
		return personas.size();
	}

	public Object getValueAt(int x, int y) {
		String retorno = "";

		Persona persona = personas.get(x);

		switch (y) {

		case 0:
			retorno = persona.getNombreEvento();
			break;
		case 1:
			retorno = persona.getNumeroPersonas();
			break;
		}

		return retorno;
	}

}
