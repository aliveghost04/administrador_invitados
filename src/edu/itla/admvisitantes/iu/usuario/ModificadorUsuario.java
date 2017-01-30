package edu.itla.admvisitantes.iu.usuario;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.ModeloTablaUsuarios;
import edu.itla.admvisitantes.modelotabla.Usuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

public class ModificadorUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JComboBox<String> cbPerfil;
	private String nombre;
	private String apellido;
	private String usuario;
	private String contraseña;
	int perfil;
	int numero;
	private static ModificadorUsuario instancia;

	public static ModificadorUsuario getInstancia(){
		
		if(instancia == null){
			instancia = new ModificadorUsuario();
		}
		return instancia;
	}
	
	private ModificadorUsuario() {
		
		setTitle("Modificar Usuario");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 13);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setFont(fuente);
		lblNombre.setBounds(10, 31, 65, 22);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido :");
		lblApellido.setFont(fuente);
		lblApellido.setBounds(10, 76, 65, 14);
		contentPane.add(lblApellido);
		
		JLabel lblContraseña = new JLabel("Contraseña :");
		lblContraseña.setFont(fuente);
		lblContraseña.setBounds(10, 166, 86, 14);
		contentPane.add(lblContraseña);
		
		JLabel lblUsuario = new JLabel("Usuario :");
		lblUsuario.setFont(fuente);
		lblUsuario.setBounds(10, 121, 65, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblPerfil = new JLabel("Perfil :");
		lblPerfil.setFont(fuente);
		lblPerfil.setBounds(10, 211, 65, 14);
		contentPane.add(lblPerfil);
		
		String[] perfiles = {"Selecione", "Administrador", "Portero"};
		
		cbPerfil = new JComboBox<String>(perfiles);
		cbPerfil.setBounds(71, 209, 108, 20);
		contentPane.add(cbPerfil);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(93, 33, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setText((String) ModeloTablaUsuarios.getInstancia().getValueAt(MantenimientoUsuarios.
																		tableListarUsuarios.getSelectedRow(), 0));
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(93, 74, 86, 20);
		contentPane.add(txtApellido);
		txtApellido.setText((String) ModeloTablaUsuarios.getInstancia().getValueAt(MantenimientoUsuarios.
																		tableListarUsuarios.getSelectedRow(), 1));
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(93, 119, 86, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setText((String) ModeloTablaUsuarios.getInstancia().getValueAt(MantenimientoUsuarios.
																		tableListarUsuarios.getSelectedRow(), 2));
		
		txtContraseña = new JTextField();
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(93, 164, 86, 20);
		contentPane.add(txtContraseña);
		txtContraseña.setText((String) ModeloTablaUsuarios.getInstancia().getValueAt(MantenimientoUsuarios.
																		tableListarUsuarios.getSelectedRow(), 3));
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombre = txtNombre.getText();
				apellido = txtApellido.getText();
				usuario = txtUsuario.getText();
				contraseña = txtContraseña.getText();
				perfil = cbPerfil.getSelectedIndex();
				
				if(nombre.equals("") || apellido.equals("") || usuario.equals("") || contraseña.equals("")){
					JOptionPane.showMessageDialog(ModificadorUsuario.this, "Debe completar todos los campos", 
								"Advertencia", JOptionPane.WARNING_MESSAGE);;
					return;
				} else {
						if(cbPerfil.getSelectedIndex() == 0){
						JOptionPane.showMessageDialog(ModificadorUsuario.this, "Debe selecionar Perfil", "Aviso",
								JOptionPane.WARNING_MESSAGE);
							return;
					} else {
						try {
							String usuarioTemp = (String) ModeloTablaUsuarios.getInstancia().getValueAt(MantenimientoUsuarios.
																	tableListarUsuarios.getSelectedRow(), 2);
							
							ResultSet rs = Conexion.getInstancia().hacerConsulta("SELECT id_usuario FROM usuarios WHERE usuario = '"+ usuarioTemp + "'");
							
							if(rs.next()){
								numero = rs.getInt("id_usuario");
							}
							Conexion.getInstancia().hacerCambio("UPDATE usuarios SET nombre_usuario ='"+nombre+"',"
									+ " apellido_usuario ='"+ apellido +"', usuario ='"+ usuario +"' ,"
									+ "clave_usuario ='"+ contraseña +"', id_cargo ="+ perfil +" WHERE id_usuario = '"+numero+"'" );
						} catch (Exception e1) {
							if (e1.getMessage().equals("Duplicate entry '" + usuario + "' for key 'usuario'")) {
								JOptionPane.showMessageDialog(ModificadorUsuario.this, "Ya existe el usuario '" + usuario + "'",
										"Error", JOptionPane.ERROR_MESSAGE);
								txtUsuario.requestFocus();
								txtUsuario.selectAll();
								return;
							} else {
								JOptionPane.showMessageDialog(ModificadorUsuario.this, "No se puede guardar el usuario ",
										"Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
							
						ModeloTablaUsuarios.getInstancia().modificar(new Usuario(nombre,apellido,usuario,contraseña,(String) cbPerfil.getSelectedItem()),
								MantenimientoUsuarios.tableListarUsuarios.getSelectedRow() );

						JOptionPane.showMessageDialog(ModificadorUsuario.this, "Cambios han sido Aplicados", "Advertencia", 
								JOptionPane.INFORMATION_MESSAGE);
						instancia = null;
						dispose();
					}
				}
			}
		});
		btnAplicar.setBounds(210, 197, 89, 23);
		contentPane.add(btnAplicar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				instancia = null;
				dispose();
			}
		});
		btnCancelar.setBounds(309, 197, 89, 23);
		contentPane.add(btnCancelar);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				instancia = null;
			}
		});
	}
}
