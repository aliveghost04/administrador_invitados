package edu.itla.admvisitantes.iu.evento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;

import com.toedter.calendar.JDateChooser;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Evento;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventos;

public class ModificadorEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtUbicacion;
	private JComboBox<String> cbHoras;
	private JComboBox<String> cbMinutos;
	private JDateChooser dateChooser;
	private String nombre;
	private String ubicacion;
	private String fecha;
	private String horaCompleta;
	private String horasTemp;
	private String minutosTemp;
	private int idTemp;
	private static ModificadorEvento instancia;
	
	public static ModificadorEvento getInstancia(){
		
		if(instancia == null){
			instancia = new ModificadorEvento();
		}
		return instancia;
	}

	private ModificadorEvento() {
		
		setTitle("Modificando Evento");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(10, 33, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblFecha = new JLabel("Fecha :");
		lblFecha.setBounds(10, 77, 46, 14);
		contentPane.add(lblFecha);
		
		JLabel lblUbicacion = new JLabel("Ubicacion");
		lblUbicacion.setBounds(10, 125, 46, 14);
		contentPane.add(lblUbicacion);
		
		JLabel lblHora = new JLabel("Hora :");
		lblHora.setBounds(10, 170, 46, 14);
		contentPane.add(lblHora);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(66, 30, 266, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setText((String) ModeloTablaEventos.getInstancia().getValueAt
		         (MantenimientoEventos.getInstancia().getFilaSeleccionada(), 1));
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(50);
		
		txtUbicacion = new JTextField();
		txtUbicacion.setColumns(10);
		txtUbicacion.setBounds(66, 122, 266, 20);
		contentPane.add(txtUbicacion);
		txtUbicacion.setText((String) ModeloTablaEventos.getInstancia().getValueAt
		         (MantenimientoEventos.getInstancia().getFilaSeleccionada(), 3));
		RestrictedTextField restriccionTxtUbicacion = new RestrictedTextField(txtUbicacion);
		restriccionTxtUbicacion.setLimit(50);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setFocusable(false);
		dateChooser.setMinSelectableDate(new Date());
		dateChooser.setBounds(66, 71, 95, 20);
		contentPane.add(dateChooser);
		
		String[] horas = new String[25];
		int j = 1;
		for (int x = 1; x <= 24; x++) {
				horas[0] = "Hora";
			if (x < 10) {
				horas[x] = "0" + x;
			} else {
				horas[j] = "" +  x;
			}
			j++;
		}
		
		cbHoras = new JComboBox<String>(horas);
		cbHoras.setBounds(66, 167, 56, 20);
		contentPane.add(cbHoras);
		
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
		cbMinutos.setBounds(130, 167, 70, 20);
		contentPane.add(cbMinutos);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				instancia = null;
				dispose();
			}
		});
		btnCancelar.setBounds(273, 213, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				
				nombre = txtNombre.getText();
				ubicacion = txtUbicacion.getText();
				
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
				} catch (Exception evento) {
					fecha = "0";
				}
				
				if (nombre.equals("") || ubicacion.equals("") ){
					JOptionPane.showMessageDialog(ModificadorEvento.this, "Debe llenar todos los campos", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (fecha.equals("0")) {
					JOptionPane.showMessageDialog(ModificadorEvento.this, "Debe Introducir una fecha válida", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (cbHoras.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(ModificadorEvento.this, "Debe Seleccionar la hora del evento", 
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
				} else  {
					horasTemp = (String) (cbHoras.getSelectedItem());
				}
			
				if (cbMinutos.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(ModificadorEvento.this, "Debe Seleccionar la hora del evento", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					minutosTemp = (String) cbMinutos.getSelectedItem();
				}
				
				horaCompleta = horasTemp + ":" + minutosTemp + ":" + "00";
				
				idTemp = Integer.parseInt((String) ModeloTablaEventos.getInstancia().
						getValueAt(MantenimientoEventos.getInstancia().getFilaSeleccionada(), 0));		
				
				
				try {
					Conexion.getInstancia().hacerCambio("  UPDATE eventos SET nombre_evento ='"+nombre+"', ubicacion_evento ="
							+ "'"+ubicacion+"', fecha_evento = '"+fecha+"', hora_evento = '"+horaCompleta+"'"
									+ "WHERE id_evento = "+idTemp+"  " );
					
					ModeloTablaEventos.getInstancia().modificar(MantenimientoEventos.getInstancia().getFilaSeleccionada(),
																	new Evento(idTemp,nombre,fecha,ubicacion));
					
					JOptionPane.showMessageDialog(ModificadorEvento.this, "Modificacion exitosa", 
							"Información", JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(ModificadorEvento.this, "Error al guardar en la base de datos", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				instancia = null;
				dispose();
				
			}
		});
		btnGuardar.setBounds(163, 213, 89, 23);
		contentPane.add(btnGuardar);
		
		addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				
				instancia = null;
			}	
		});
	}
}
