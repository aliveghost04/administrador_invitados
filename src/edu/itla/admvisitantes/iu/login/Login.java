package edu.itla.admvisitantes.iu.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.iu.InterfazUsuario;
import edu.itla.admvisitantes.properties.ManejadorProperties;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel etiquetaUsuario;
	private JLabel etiquetaContraseña;
	private JLabel etiquetaLoginFallido;
	private JTextField campoUsuario;
	private JPasswordField campoContraseña;
	private JButton btnLogearse;
	private String usuario;
	private String contraseña;
	private String usuarioBD;
	private String contraseñaBD;
	private int idCargo;
	private Conexion conexion;
	private InterfazUsuario interfazUsuario;
	
	public Login() {
		
		setTitle("Iniciar Sesión");
		setSize(250, 170);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		if (new ManejadorProperties().cargar() == null) {
			System.exit(0);
		}
		
		etiquetaLoginFallido = new JLabel("*Usuario y/o Contraseña Incorrectos");
		etiquetaLoginFallido.setBounds(15, 110, 210, 30);
		etiquetaLoginFallido.setForeground(Color.red);
		Font fuenteLoginFallido = new Font("Arial", Font.BOLD, 12);
		etiquetaLoginFallido.setFont(fuenteLoginFallido);
		add(etiquetaLoginFallido);
		etiquetaLoginFallido.setVisible(false);
		
		etiquetaUsuario = new JLabel("Usuario: ");
		etiquetaUsuario.setBounds(10, 10, 60, 20);
		
		etiquetaContraseña = new JLabel("Contraseña: ");
		etiquetaContraseña.setBounds(10, 40, 90, 20);
		
		campoUsuario = new JTextField();
		campoUsuario.setBounds(90, 10, 150, 20);
		campoUsuario.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					campoContraseña.requestFocus();
				} else {
					etiquetaLoginFallido.setVisible(false);
				}
			}
		});
		
		campoContraseña = new JPasswordField();
		campoContraseña.setBounds(90, 40, 150, 20);
		campoContraseña.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					new btnLogearseActionPerformed().actionPerformed(null);
				} else {
					etiquetaLoginFallido.setVisible(false);
				}
			}
		});
		
		btnLogearse = new JButton("Iniciar Sesión");
		btnLogearse.setBounds(40, 80, 150, 30);
		btnLogearse.addActionListener(new btnLogearseActionPerformed());
		
		add(etiquetaUsuario);
		add(etiquetaContraseña);
		add(campoUsuario);
		add(campoContraseña);
		add(btnLogearse);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if (Conexion.getInstancia() != null){
						Conexion.getInstancia().cerrarConexion();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	private class btnLogearseActionPerformed implements ActionListener {

		public void actionPerformed(ActionEvent evento) {
			
			usuario = campoUsuario.getText();
			contraseña = new String(campoContraseña.getPassword());
			try {
				conexion = Conexion.getInstancia();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Login.this, "No se puede conectar con la Base de Datos",
										"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				ResultSet resultado = conexion.hacerConsulta("SELECT usuario, "
						+ "clave_usuario, id_cargo FROM usuarios WHERE usuario = '" + usuario + "'");
				while(resultado.next()){
					usuarioBD = resultado.getString("usuario");
					contraseñaBD = resultado.getString("clave_usuario");
					idCargo = resultado.getInt("id_cargo");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Login.this, "Hay un error con la base de datos",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (usuario.equalsIgnoreCase(usuarioBD) && contraseña.equals(contraseñaBD)) {
				 interfazUsuario = new InterfazUsuario(usuario);
				switch (idCargo) {
				case (1):
					interfazUsuario.asignarAdministrador();
				break;
				case (2):
					interfazUsuario.setVisible(true);
				break;
				}
				dispose();
			} else {
				etiquetaLoginFallido.setVisible(true);
			}
		}
	}
}
