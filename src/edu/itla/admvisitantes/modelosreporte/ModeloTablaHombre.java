package edu.itla.admvisitantes.modelosreporte;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import edu.itla.admvisitantes.bd.Conexion;

public class ModeloTablaHombre extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private static ModeloTablaHombre instancia;
	private ArrayList<Hombre> hombres;
	String nombre;
	int cantidadHombres;
	
	String[] encabezados = {"  Nombre del Evento  "," # Hombres "};
	
	private ModeloTablaHombre(){
		
		hombres= new ArrayList<Hombre>();
		try {
		ResultSet retorno =Conexion.getInstancia().hacerConsulta("SELECT nombre_evento FROM eventos ");
		
		while(retorno.next()){
			nombre = retorno.getString("nombre_evento");
			cantidadHombres = 0;
			hombres.add(new Hombre(nombre, cantidadHombres));
			
		}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static ModeloTablaHombre getInstancia(){
		
		if(instancia == null){
			instancia = new ModeloTablaHombre();
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
		return hombres.size();
	}


	public Object getValueAt(int x, int y) {
		String retorno = "";
		
		Hombre hombre = hombres.get(x);
		
		switch(y){
		
		case 0: retorno = hombre.getNombreEvento();
				break;
		case 1: retorno = hombre.getNumeroPersonas();		
				break;
		}
		
		
		return retorno;
	}
	
	
	
}
