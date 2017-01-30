package edu.itla.admvisitantes.reporte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import edu.itla.admvisitantes.modelosreporte.ModeloTablaVisita;

public class EventosMasVisitados extends JFrame {

	private static final long serialVersionUID = 1L;
	private static EventosMasVisitados instancia;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollpane;

	public static EventosMasVisitados getInstancia(){
		
		if(instancia == null){
			instancia = new EventosMasVisitados();
		}
		return instancia;
	}


	private  EventosMasVisitados() {
		
		setTitle("Eventos más Visitados");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(500, 200, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable(ModeloTablaVisita.getInstancia());
		
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(10, 11, 414, 188);
		contentPane.add(scrollpane);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				instancia = null;
				dispose();
			}
		});
		btnSalir.setBounds(335, 227, 89, 23);
		contentPane.add(btnSalir);
		
		
		
		
		
		
		
		
		
	}

}
