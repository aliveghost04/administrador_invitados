package edu.itla.admvisitantes.modelosreporte;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Evento;

public class ModeloTablaAsistencia extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaAsistencia instancia;
	private ArrayList<Asistencia> porciento;
	private ArrayList<Evento> evento;
	int numero;
	String nombreEvento;
	int porcientoPersonas;

	String[] encabezados = { "Nombre del Evento", "%" };

	private ModeloTablaAsistencia() {

		porciento = new ArrayList<Asistencia>();
		evento = new ArrayList<Evento>();

		try {

			ResultSet valor = Conexion.getInstancia().hacerConsulta(
					"Select id_evento, nombre_evento, "
							+ "fecha_evento, ubicacion_evento from eventos");

			while (valor.next()) {
				evento.add(new Evento(valor.getInt(1), valor.getString(2),
						valor.getString(3), valor.getString(4)));
			}

			for (int x = 0; x < evento.size(); x++) {

				ResultSet total = Conexion.getInstancia()
						.hacerConsulta("SELECT count(id_invitado) "
										+ "from invitados natural join eventos where id_evento = "
										+ evento.get(x).getIdEvento()
										+ " GROUP BY nombre_evento");

				while (total.next()) {
					numero = total.getInt(1);
				}

				ResultSet presentes = Conexion.getInstancia()
						.hacerConsulta("Select nombre_evento , count(id_invitado) "
										+ "from invitados natural join eventos where asistencia_invitado = 1"
										+ " and id_evento = "
										+ evento.get(x).getIdEvento()
										+ " GROUP BY nombre_evento");

				while (presentes.next()) {

					nombreEvento = presentes.getString("nombre_evento");
					porcientoPersonas = ((presentes.getInt(2) * 100) / numero);
					porciento.add(new Asistencia(nombreEvento,
							porcientoPersonas));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ModeloTablaAsistencia getInstancia() {

		if (instancia == null) {
			instancia = new ModeloTablaAsistencia();
		}
		return instancia;
	}

	public int getColumnCount() {
		return encabezados.length;
	}

	public int getRowCount() {

		return porciento.size();
	}

	public String getColumnName(int column) {

		return encabezados[column];
	}

	public Object getValueAt(int x, int y) {

		String retorno = "";
		Asistencia por = porciento.get(x);

		switch (y) {
		case 0:
			retorno = por.getNombreEvento();
			break;
		case 1:
			retorno = por.getPorciento();
			break;
		}

		return retorno;
	}

}
