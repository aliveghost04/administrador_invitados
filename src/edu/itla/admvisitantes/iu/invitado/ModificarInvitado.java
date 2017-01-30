package edu.itla.admvisitantes.iu.invitado;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

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

import Atxy2k.CustomTextField.RestrictedTextField;
import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Invitado;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventos;
import edu.itla.admvisitantes.modelotabla.ModeloTablaInvitados;

public class ModificarInvitado extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtApellido;
	private JComboBox<String> cbSexo;
	private JButton btnSeleccionar;
	private JTable tblEventos;
	private JScrollPane contenedorTabla;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String sexo;
	private int idEvento;
	private static ModificarInvitado instancia;
	
	public static ModificarInvitado getInstancia() {
		
		if(instancia == null){
			instancia = new ModificarInvitado();
		}		
		return instancia;
	}

	private ModificarInvitado() {
		
		setTitle("Modificando Invitado");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(10, 34, 64, 20);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido :");
		lblApellido.setBounds(184, 37, 58, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDireccion = new JLabel("Direccion :");
		lblDireccion.setBounds(10, 131, 86, 20);
		contentPane.add(lblDireccion);
		
		JLabel lblTelefono = new JLabel("Telefono  :");
		lblTelefono.setBounds(10, 79, 86, 20);
		contentPane.add(lblTelefono);
		
		JLabel lblSexo = new JLabel("Sexo :");
		lblSexo.setBounds(10, 183, 46, 14);
		contentPane.add(lblSexo);
		
		JLabel lblEvento = new JLabel("Evento :");
		lblEvento.setBounds(211, 183, 46, 14);
		contentPane.add(lblEvento);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(70, 34, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setText((String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
																getFilaSeleccionada(), 0));
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(30);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(70, 79, 86, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		txtTelefono.setText((String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
																getFilaSeleccionada(), 2));
		RestrictedTextField restriccionTxtTelefono = new RestrictedTextField(txtTelefono);
		restriccionTxtTelefono.setLimit(10);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(70, 131, 185, 20);
		contentPane.add(txtDireccion);
		txtDireccion.setText((String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
																getFilaSeleccionada(), 3));
		RestrictedTextField restriccionTxtDireccion = new RestrictedTextField(txtDireccion);
		restriccionTxtDireccion.setLimit(70);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(252, 34, 86, 20);
		contentPane.add(txtApellido);
		txtApellido.setText((String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
																		getFilaSeleccionada(), 1));
		RestrictedTextField restriccionTxtApellido = new RestrictedTextField(txtApellido);
		restriccionTxtApellido.setLimit(30);
		
		tblEventos = new JTable(ModeloTablaEventos.getInstancia());
		contenedorTabla = new JScrollPane(tblEventos);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				idEvento = JOptionPane.showConfirmDialog(null, contenedorTabla, 
						"Seleccione un evento", JOptionPane.OK_CANCEL_OPTION);
				switch (idEvento) {
				case (JOptionPane.OK_OPTION):
					idEvento = ModeloTablaEventos.getInstancia().getIdEvento(tblEventos.getSelectedRow());
				btnSeleccionar.setText("" + idEvento);
				break;
				case (JOptionPane.CANCEL_OPTION):
					return;
				}
			}
		});
		btnSeleccionar.setBounds(272, 179, 119, 23);
		contentPane.add(btnSeleccionar);
		
		String [] generos = {"Seleccione", "M", "F"};
		
		cbSexo = new JComboBox<String>(generos);
		cbSexo.setBounds(70, 180, 100, 20);
		contentPane.add(cbSexo);
	
		JButton btnGuardar = new JButton("--Guardar--");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombre    = txtNombre.getText();
				apellido  = txtApellido.getText();
				telefono  = txtTelefono.getText();
				direccion = txtDireccion.getText();
				sexo      = (String) cbSexo.getSelectedItem();
				
				try {
					Long.parseLong(telefono);
				} catch (Exception e2) {
					telefono = "0";
				}
				
				if(nombre.equals("") || apellido.equals("") || direccion.equals("")){
					JOptionPane.showMessageDialog(ModificarInvitado.this, "Debe llenar todos los campos",
														"Información", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (telefono.equals("0")) {
					JOptionPane.showMessageDialog(ModificarInvitado.this, "Introduzca un teléfono válido",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
					txtTelefono.requestFocus();
					txtTelefono.selectAll();
					return;
				} else if (cbSexo.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(ModificarInvitado.this, "Debe seleccionar el sexo",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
					
				} else if(btnSeleccionar.getText().equals("Seleccionar")){
					JOptionPane.showMessageDialog(ModificarInvitado.this, "Debe seleccionar un evento",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					
					try {
						Conexion.getInstancia().hacerCambio("UPDATE invitados SET nombre_invitado ='"+nombre+"',"
								+ " apellido_invitado ='"+apellido+"', telefono_invitado ='"+telefono+"', "
										+ "direccion_invitado ='"+direccion+"', sexo_invitado = '"+sexo+"', "
												+ "id_evento = "+idEvento+" WHERE id_invitado = " + getIdInvitado());
					} catch (Exception e1) {
						System.out.println(getIdInvitado());
						JOptionPane.showMessageDialog(ModificarInvitado.this, "Problemas con la base de datos", "Error",
                                JOptionPane.ERROR_MESSAGE);
			
					} 
					
					ModeloTablaInvitados.getInstancia().modificar(MantenimientoInvitados.getInstancia().
							getFilaSeleccionada(), new Invitado(getIdInvitado(), nombre ,apellido ,telefono,
																	direccion, sexo, getNombreEvento(), false));

					JOptionPane.showMessageDialog(ModificarInvitado.this, "Modificacion exitosa", "Aviso",
							                                        JOptionPane.INFORMATION_MESSAGE);
					
					instancia = null;
					dispose();
				} 
				
			}
		});
		btnGuardar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		btnGuardar.setBounds(206, 215, 104, 35);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				instancia = null;
				dispose();
				
			}
		});
		btnCancelar.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		btnCancelar.setBounds(320, 215, 104, 35);
		contentPane.add(btnCancelar);
		
		addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				
				instancia = null;
			}	
		});
				
	}
	
	public int getIdInvitado(){
		
		ResultSet id = null;
		int idInvitado = 0;
		nombre = (String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
				getFilaSeleccionada(), 0);
		
		apellido = (String) ModeloTablaInvitados.getInstancia().getValueAt(MantenimientoInvitados.getInstancia().
				getFilaSeleccionada(), 1);
		
		try {
			
			id = Conexion.getInstancia().hacerConsulta("SELECT id_invitado FROM invitados WHERE "
									+ "nombre_invitado ='"+nombre+"' AND apellido_invitado ='"+apellido+"'");
			
			while(id.next()){
				idInvitado = id.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idInvitado;
	}
	

	public String getNombreEvento(){
		
		int eventoTemp = Integer.parseInt(btnSeleccionar.getText());
		String nombreEvento = null;
		try {
			
			ResultSet nombreEvent = Conexion.getInstancia().hacerConsulta("SELECT nombre_evento FROM eventos "
																			+ "WHERE id_evento = "+eventoTemp+"");
			
			while(nombreEvent.next()){
				
				nombreEvento = nombreEvent.getString("nombre_evento");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nombreEvento;
	}
}
