package edu.itla.admvisitantes.iu.usuario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.ModeloTablaUsuarios;
import edu.itla.admvisitantes.modelotabla.Usuario;

public class MantenimientoUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTable tableListarUsuarios;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JTextField txtContrasena;
	public JComboBox<String> cbPerfilUsuario;
	private String nombre;
	private String apellido;
	private String usuario;
	private	String contrasena;
	private int perfilUsuario;
	private static MantenimientoUsuarios instancia;
	
	public static MantenimientoUsuarios getInstancia() {
		
		if (instancia == null) {
			instancia = new MantenimientoUsuarios();
		}
		return instancia;
	}
	
	private MantenimientoUsuarios() {
		
		setTitle("Mantenimiento de Usuarios");
		setBounds(100, 100, 590, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		tableListarUsuarios = new JTable(ModeloTablaUsuarios.getInstancia());
		
		JScrollPane scrollPane = new JScrollPane(tableListarUsuarios);
		scrollPane.setBounds(10, 11, 564, 339);
		contentPane.add(scrollPane);
		
		JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tableListarUsuarios.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(MantenimientoUsuarios.this, "Debe seleccionar un usuario", 
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					ModeloTablaUsuarios.getInstancia().eliminar(tableListarUsuarios.getSelectedRow());
				}
			}
		});
		btnEliminarUsuario.setBounds(10, 361, 131, 23);
		contentPane.add(btnEliminarUsuario);
		
		JButton btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ModificadorUsuario.getInstancia().setVisible(true);
				} catch (java.lang.ArrayIndexOutOfBoundsException exception) {
					JOptionPane.showMessageDialog(MantenimientoUsuarios.this, "Debe seleccionar un usuario", 
							                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnModificarUsuario.setBounds(459, 361, 115, 23);
		contentPane.add(btnModificarUsuario);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 391, 564, 170);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblAgregarUsuarios = new JLabel("Agregar Usuarios");
		lblAgregarUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAgregarUsuarios.setBounds(230, 0, 133, 23);
		panel.add(lblAgregarUsuarios);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 24, 46, 14);
		panel.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(10, 49, 46, 14);
		panel.add(lblApellido);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(10, 74, 46, 14);
		panel.add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("Contraseña: ");
		lblContrasena.setBounds(10, 99, 66, 14);
		panel.add(lblContrasena);
		
		JLabel lblPerfilDelUsuario = new JLabel("Perfil del Usuario: ");
		lblPerfilDelUsuario.setBounds(10, 124, 87, 14);
		panel.add(lblPerfilDelUsuario);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(96, 21, 111, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(25);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(96, 46, 111, 20);
		panel.add(txtApellido);
		RestrictedTextField restriccionTxtApellido = new RestrictedTextField(txtApellido);
		restriccionTxtApellido.setLimit(25);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(96, 71, 111, 20);
		panel.add(txtUsuario);
		RestrictedTextField restriccionTxtUsuario = new RestrictedTextField(txtUsuario);
		restriccionTxtUsuario.setLimit(15);
		
		txtContrasena = new JTextField();
		txtContrasena.setColumns(10);
		txtContrasena.setBounds(96, 96, 111, 20);
		panel.add(txtContrasena);
		RestrictedTextField restriccionTxtContraseña = new RestrictedTextField(txtContrasena);
		restriccionTxtContraseña.setLimit(20);
		
		String[] eleccion = {"Seleccione", "Administrador", "Portero"};
		cbPerfilUsuario = new JComboBox<String>(eleccion);
		cbPerfilUsuario.setBounds(96, 121, 111, 17);
		panel.add(cbPerfilUsuario);
		
		JButton btnAgregarUsuario = new JButton("Agregar Usuario");
		btnAgregarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				nombre = txtNombre.getText();
				apellido = txtApellido.getText();
				usuario = txtUsuario.getText();
				contrasena = txtContrasena.getText();
				perfilUsuario = cbPerfilUsuario.getSelectedIndex();
				String temporal = null;
				
				switch (perfilUsuario) {
				case (0):
					JOptionPane.showMessageDialog(MantenimientoUsuarios.this, "Debe seleccionar un perfil de usuario",
							"Información", JOptionPane.INFORMATION_MESSAGE);
					return;	
				case (1):
					temporal = "Administrador";
				break;
				case (2):
					temporal = "Portero";
				break;
				}
				
				try {
					Conexion.getInstancia().hacerCambio("INSERT INTO usuarios values(null, "
							+ "1, " + perfilUsuario + ", '" + nombre + "', '" + apellido +
							"', '" + usuario + "', '" + contrasena + "')");
				} catch (Exception e) {
					if (e.getMessage().equals("Duplicate entry '" + usuario + "' for key 'usuario'")) {
						JOptionPane.showMessageDialog(MantenimientoUsuarios.this, "Ya existe el usuario '" + usuario + "'",
								"Error", JOptionPane.ERROR_MESSAGE);
						txtUsuario.requestFocus();
						txtUsuario.selectAll();
						return;
					} else {
						JOptionPane.showMessageDialog(MantenimientoUsuarios.this, "No se puede guardar el usuario ",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				Usuario instanciaUsuario = new Usuario(nombre, apellido, usuario, contrasena, temporal);
				ModeloTablaUsuarios.getInstancia().agregar(instanciaUsuario);
				
				txtNombre.setText("");
				txtApellido.setText("");
				txtUsuario.setText("");
				txtContrasena.setText("");
			}
		});
		btnAgregarUsuario.setBounds(230, 120, 111, 23);
		panel.add(btnAgregarUsuario);
		
		addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
				
				instancia = null;
			}
		});
		
	}
}
