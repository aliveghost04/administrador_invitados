package edu.itla.admvisitantes.modelosreporte;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaVisita extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static ModeloTablaVisita instancia;
	private ArrayList<Visita> visitas;
	String nombre;
	int cantidadPersonas;

	String[] encabezados = { "  Nombre del Evento  ", " # Visitas" };

	private ModeloTablaVisita() {

		visitas = new ArrayList<Visita>();
		try {
			ResultSet retorno = Conexion.getInstancia().
					hacerConsulta("SELECT nombre_evento FROM eventos ");

			while (retorno.next()) {
				nombre = retorno.getString("nombre_evento");
				cantidadPersonas = 0;
				visitas.add(new Visita(nombre, cantidadPersonas));

			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static ModeloTablaVisita getInstancia() {

		if (instancia == null) {
			instancia = new ModeloTablaVisita();
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
		return visitas.size();
	}

	public Object getValueAt(int x, int y) {
		String retorno = "";

		Visita vis = visitas.get(x);

		switch (y) {

		case 0:
			retorno = vis.getNombreEvento();
			break;
		case 1:
			retorno = vis.getNumeroPersonas();
			break;
		}

		return retorno;
	}

}
