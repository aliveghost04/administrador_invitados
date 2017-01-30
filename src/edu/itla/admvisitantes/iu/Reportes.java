package edu.itla.admvisitantes.iu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.itla.admvisitantes.reporte.EventosMasVisitados;
import edu.itla.admvisitantes.reporte.EventosMasVisitasHombres;
import edu.itla.admvisitantes.reporte.EventosMasVisitasMujeres;
import edu.itla.admvisitantes.reporte.PersonasPorEventos;
import edu.itla.admvisitantes.reporte.PorcientoAsistencia;


public class Reportes extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private static Reportes instancia; 

	public static Reportes getInstancia(){
		
		if(instancia == null){
			instancia = new Reportes();
		}
		return instancia;
	}

	private Reportes() {
		
		setTitle("Reportes");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(500, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JLabel lblCantidadDePersonas = new JLabel("1) Cantidad de Personas que Asistieron por eventos :");
		lblCantidadDePersonas.setBounds(10, 24, 315, 20);
		contentPane.add(lblCantidadDePersonas);
		
		JButton btnN1 = new JButton("** Ver **");
		btnN1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PersonasPorEventos.getInstancia().setVisible(true);
				
			}
		});
		btnN1.setBounds(335, 23, 89, 23);
		contentPane.add(btnN1);
		
		JLabel lblPorciento_1 = new JLabel("2) Porciento de personas en la lista que asistieron ");
		lblPorciento_1.setBounds(10, 68, 329, 14);
		contentPane.add(lblPorciento_1);
		
		JLabel lblPorciento_2 = new JLabel("a evento :");
		lblPorciento_2.setBounds(20, 81, 83, 14);
		contentPane.add(lblPorciento_2);
		
		
		JButton btnN2 = new JButton("** Ver **");
		btnN2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				PorcientoAsistencia.getInstancia().setVisible(true);
				
			}
		});
		btnN2.setBounds(335, 64, 89, 23);
		contentPane.add(btnN2);
		
		JLabel lblEventosMasVisitados = new JLabel("4) Eventos mas visitados por mujeres :");
		lblEventosMasVisitados.setBounds(10, 144, 248, 14);
		contentPane.add(lblEventosMasVisitados);
		
		JLabel lblEventosMasVisitados_1 = new JLabel("3) Eventos mas Visitados :");
		lblEventosMasVisitados_1.setBounds(10, 106, 173, 14);
		contentPane.add(lblEventosMasVisitados_1);
		
		JButton btnN3 = new JButton("** Ver **");
		btnN3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventosMasVisitados.getInstancia().setVisible(true);
				
			}
		});
		btnN3.setBounds(335, 102, 89, 23);
		contentPane.add(btnN3);
		
		JButton btnN4 = new JButton("** Ver **");
		btnN4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				EventosMasVisitasMujeres.getInstancia().setVisible(true);
				
			}
		});
		btnN4.setBounds(335, 140, 89, 23);
		contentPane.add(btnN4);
		
		JLabel lblEventosMasVisitados_2 = new JLabel("5) Eventos mas visitados por hombres :");
		lblEventosMasVisitados_2.setBounds(10, 182, 248, 14);
		contentPane.add(lblEventosMasVisitados_2);
		
		JLabel lblDiasDeLa = new JLabel("6) Dias de la semana en que asisten mas personas a");
		lblDiasDeLa.setBounds(10, 220, 341, 14);
		contentPane.add(lblDiasDeLa);
	
		JLabel lblEventos = new JLabel("eventos :");
		lblEventos.setBounds(20, 236, 73, 14);
		contentPane.add(lblEventos);
		
		JButton btnN5 = new JButton("** Ver **");
		btnN5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventosMasVisitasHombres.getInstancia().setVisible(true);
								
			}
		});
		btnN5.setBounds(335, 178, 89, 23);
		contentPane.add(btnN5);
		
		JButton btnN6 = new JButton("** Ver **");
		btnN6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnN6.setBounds(335, 216, 89, 23);
		contentPane.add(btnN6);
		
	}
}