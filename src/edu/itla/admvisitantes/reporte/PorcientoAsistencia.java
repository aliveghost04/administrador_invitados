package edu.itla.admvisitantes.reporte;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

import edu.itla.admvisitantes.modelosreporte.ModeloTablaAsistencia;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PorcientoAsistencia extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private static PorcientoAsistencia instancia;
	
	public static PorcientoAsistencia getInstancia(){
		
		if(instancia == null){
			instancia = new PorcientoAsistencia();
		}
		return instancia;
	}
	
	public PorcientoAsistencia() {
		
		setTitle("Porciento de Personas por Eventos");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(500, 200, 450, 300);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable(ModeloTablaAsistencia.getInstancia());
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 11, 402, 192);
		contentPane.add(scrollPane);
		
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
