package edu.itla.admvisitantes.modelosreporte;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaMujeres extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaMujeres instancia;
	private ArrayList<Mujer> mujeres;
	private ResultSet retorno;
	String nombre;
	int cantidadMujeres;

	private String[] encabezados = { "  Nombre del Evento  ", " # Mujeres " };

	private ModeloTablaMujeres() {

		mujeres = new ArrayList<Mujer>();
		try {
		retorno = Conexion.getInstancia().hacerConsulta("SELECT nombre_evento, "
					+ "COUNT(*) FROM eventos NATURAL JOIN invitados WHERE asistencia_invitado = 1"
					+ " AND sexo_invitado = 'F' GROUP BY nombre_evento order by count(*)");

			while (retorno.next()) {
				nombre = retorno.getString("nombre_evento");
				cantidadMujeres = retorno.getInt(2);
				mujeres.add(new Mujer(nombre, cantidadMujeres));
				System.out.println(nombre + cantidadMujeres);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static ModeloTablaMujeres getInstancia() {

		if (instancia == null) {
			instancia = new ModeloTablaMujeres();
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
		return mujeres.size();
	}

	public Object getValueAt(int x, int y) {
		String retorno = "";

		Mujer mujer = mujeres.get(x);

		switch (y) {

		case 0:
			retorno = mujer.getNombreEvento();
			break;
		case 1:
			retorno = mujer.getNumeroPersonas();
			break;
		}

		return retorno;
	}

}
