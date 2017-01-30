package edu.itla.admvisitantes.iu.evento;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Atxy2k.CustomTextField.RestrictedTextField;

import com.toedter.calendar.JDateChooser;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Evento;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventos;

public class MantenimientoEventos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEventos;
	private JPanel panel;
	private JLabel lblAgregarUnEvento;
	private JLabel lblNombre;
	private JLabel lblFecha;
	private JLabel lblUbicacion;
	private JTextField txtNombre;
	private JTextField txtUbicacion;
	private JButton btnAgregarEvento;
	private JButton btnEliminarEvento;
	private JButton btnModificarEvento;
	private JDateChooser dtcFecha;
	private JComboBox<String> cbHora;
	private JComboBox<String> cbMinutos; 
	private String nombre;
	private String fecha;
	private String hora;
	private String ubicacion;
	private String horas24;
	private String minutos60;
	private static MantenimientoEventos instancia;
	private int idEvento;
	
	public static MantenimientoEventos getInstancia() {
		
		if (instancia == null) {
			instancia = new MantenimientoEventos();
		}
		return instancia;
	}
	
	public int getFilaSeleccionada() {
		return tableEventos.getSelectedRow();
	}
	
	private MantenimientoEventos() {
		
		setTitle("Mantenimiento de Eventos");
		setBounds(100, 100, 600, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		tableEventos = new JTable(ModeloTablaEventos.getInstancia());
				
		JScrollPane scrollPane = new JScrollPane(tableEventos);
		scrollPane.setBounds(10, 11, 574, 250);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 302, 574, 149);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblAgregarUnEvento = new JLabel("Agregar un evento");
		lblAgregarUnEvento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAgregarUnEvento.setBounds(130, 0, 170, 26);
		panel.add(lblAgregarUnEvento);
		
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 33, 46, 14);
		panel.add(lblNombre);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(299, 37, 46, 14);
		panel.add(lblFecha);
		
		dtcFecha = new JDateChooser();
		dtcFecha.setDateFormatString("dd/MM/yyyy");
		dtcFecha.setBounds(355, 33, 150, 20);
		dtcFecha.setFocusable(false);
		dtcFecha.setMinSelectableDate(new Date());
		panel.add(dtcFecha);
		
		lblUbicacion = new JLabel("Ubicación: ");
		lblUbicacion.setBounds(10, 80, 57, 14);
		panel.add(lblUbicacion);
		
		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setBounds(66, 30, 150, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(50);
		
		txtUbicacion = new JTextField();
		txtUbicacion.setText("");
		txtUbicacion.setColumns(10);
		txtUbicacion.setBounds(66, 77, 150, 20);
		panel.add(txtUbicacion);
		RestrictedTextField restriccionTxtUbicacion = new RestrictedTextField(txtUbicacion);
		restriccionTxtUbicacion.setLimit(50);
		
		String[] horas = new String[25];
		for (int x = 1; x <= 24; x++) {
			horas[0] = "Hora";
			if (x <= 10) {
				horas[x] = "0" + (x - 1);
			} else {
				horas[x] = "" + (x - 1);
			}
		}
		cbHora = new JComboBox<String>(horas);
		cbHora.setBounds(355, 77, 52, 20);
		panel.add(cbHora);
		
		String[] minutos = new String[13];
		int y = 1;
		for (int x = 0; x < 60; x += 5) {
			minutos[0] = "Minuto";
			if (x < 10) {
				minutos[y] = "0" + x;
			} else {
				minutos[y] = "" + x;
			}
			y++;
		}
		cbMinutos = new JComboBox<String>(minutos);
		cbMinutos.setBounds(419, 77, 57, 20);
		panel.add(cbMinutos);
		
		btnAgregarEvento = new JButton("Agregar Evento");
		btnAgregarEvento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				nombre = txtNombre.getText();
				
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").format(dtcFecha.getDate());
				} catch (Exception e) {
					fecha = "0";
				}
				
				ubicacion = txtUbicacion.getText();
				
				if (nombre.equals("") || ubicacion.equals("") ){
					JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe llenar todos los campos", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (fecha.equals("0")) {
					JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe Introducir una fecha válida", 
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					if (cbHora.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe Seleccionar la hora del evento", 
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						horas24 = (String) (cbHora.getSelectedItem());
					}
					if (cbMinutos.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe Seleccionar la hora del evento", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						minutos60 = (String) cbMinutos.getSelectedItem();
					}
					
					hora = horas24 + ":" + minutos60 + ":" + "00";
					
					try {
						Conexion.getInstancia().hacerCambio("INSERT INTO eventos VALUES(null, "
								+ "'" + nombre + "', '" + ubicacion + "', '" + fecha + "', '" + hora + "')");
						ResultSet rs = Conexion.getInstancia().hacerConsulta("SELECT last_insert_id() from eventos");
						if (rs.next()) {
							idEvento = rs.getInt(1);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(MantenimientoEventos.this, "Error al guardar en la base de datos", 
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					Evento evento = new Evento(idEvento, nombre, fecha + " " + hora, ubicacion);
					ModeloTablaEventos.getInstancia().agregar(evento);
					
					txtNombre.setText("");
					txtUbicacion.setText("");
					cbHora.setSelectedIndex(0);
					cbMinutos.setSelectedIndex(0);
				}
			}
		});
		btnAgregarEvento.setBounds(230, 115, 109, 23);
		panel.add(btnAgregarEvento);
		
		JLabel lblHora = new JLabel("Hora: ");
		lblHora.setBounds(299, 80, 46, 14);
		panel.add(lblHora);
		
		JLabel label = new JLabel(":");
		label.setBounds(408, 80, 46, 14);
		panel.add(label);
		
		btnEliminarEvento = new JButton("Eliminar Evento");
		btnEliminarEvento.setBounds(10, 272, 115, 23);
		btnEliminarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableEventos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe elegir un evento", 
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int resultado = JOptionPane.showConfirmDialog(MantenimientoEventos.this, "Este evento puede contener invitados, si "
							+ "elimina este evento, se eliminaran los invitados asignados a este evento \n¿Desea eliminarlo de todas formas?",
							"¿Pregunta?", JOptionPane.YES_NO_OPTION);
					switch (resultado) {
					case (JOptionPane.YES_OPTION):
						ModeloTablaEventos.getInstancia().eliminar(tableEventos.getSelectedRow());
					break;
					case (JOptionPane.NO_OPTION):
						return;
					}
				}
			}
			
		});
		contentPane.add(btnEliminarEvento);
		
		btnModificarEvento = new JButton("Modificar Evento");
		btnModificarEvento.setBounds(469, 272, 115, 23);
		btnModificarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEventos.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(MantenimientoEventos.this, "Debe seleccionar un Evento",
														"Aviso", JOptionPane.ERROR_MESSAGE);
					}else{
						ModificadorEvento.getInstancia().setVisible(true);
					}
			}
		});
		contentPane.add(btnModificarEvento);
		
		addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
				instancia = null;
				// hacer una manera de que se actualice la lista
			}
		});
	}
}
