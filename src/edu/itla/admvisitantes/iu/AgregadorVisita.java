package edu.itla.admvisitantes.iu;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import edu.itla.admvisitantes.bd.Conexion;

public class AgregadorVisita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JComboBox<String> cbSexo;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String sexo;
	private static AgregadorVisita instancia;
	private static int idInvitado;
	
	public static AgregadorVisita getInstancia(int idInvitado) {
		
		AgregadorVisita.idInvitado = idInvitado;
		if (instancia == null) {
			instancia = new AgregadorVisita();
		}
		return instancia;
	}
	
	private AgregadorVisita() {
		
		setTitle("Agregar Visita");
		setResizable(false);
		setBounds(100, 100, 320, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 13);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 35, 73, 20);
		lblNombre.setFont(fuente);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(10, 66, 96, 20);
		lblApellido.setFont(fuente);
		contentPane.add(lblApellido);
		
		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(10, 97, 96, 20);
		lblTelfono.setFont(fuente);
		contentPane.add(lblTelfono);
		
		JLabel lblDireccion = new JLabel("Dirección: ");
		lblDireccion.setBounds(10, 128, 96, 20);
		lblDireccion.setFont(fuente);
		contentPane.add(lblDireccion);
		
		JLabel lblSexo = new JLabel("Sexo: ");
		lblSexo.setBounds(10, 159, 96, 20);
		lblSexo.setFont(fuente);
		contentPane.add(lblSexo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(94, 36, 150, 19);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(25);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(94, 67, 150, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		RestrictedTextField restriccionTxtApellido = new RestrictedTextField(txtApellido);
		restriccionTxtApellido.setLimit(25);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(94, 97, 150, 20);
		contentPane.add(txtTelefono);
		RestrictedTextField restriccionTxtTelefono = new RestrictedTextField(txtTelefono);
		restriccionTxtTelefono.setLimit(10);
		restriccionTxtTelefono.setOnlyNums(true);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(94, 128, 150, 20);
		contentPane.add(txtDireccion);
		RestrictedTextField restriccionTxtDireccion = new RestrictedTextField(txtDireccion);
		restriccionTxtDireccion.setLimit(50);
		
		String[] tipoSexo = {"Seleccione", "M", "F"};
		cbSexo = new JComboBox<String>(tipoSexo);
		cbSexo.setBounds(94, 159, 96, 20);
		contentPane.add(cbSexo);
		
		JButton btnAgregarVisita = new JButton("Agregar Visita");
		btnAgregarVisita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				nombre = txtNombre.getText();
				apellido = txtApellido.getText();
				telefono = txtTelefono.getText();
				direccion = txtDireccion.getText();
				
				try {
					Long.parseLong(telefono);
				} catch (Exception e) {
					telefono = "0";
				}
				
				if (nombre.equals("") || apellido.equals("") || direccion.equals("")) {
					JOptionPane.showMessageDialog(AgregadorVisita.this, "Debe llenar todos los campos",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (telefono.equals("0")) {
					JOptionPane.showMessageDialog(AgregadorVisita.this, "Introduzca un teléfono válido",
							"Error", JOptionPane.ERROR_MESSAGE);
					txtTelefono.requestFocus();
					txtTelefono.selectAll();
					return;
				}
				
				switch (cbSexo.getSelectedIndex()) {
				case (0):
					JOptionPane.showMessageDialog(AgregadorVisita.this, "Debe Seleccionar el sexo",
							"Información", JOptionPane.WARNING_MESSAGE);
				return;
				case (1):
					sexo = "M";
				break;
				case (2):
					sexo = "F";
				break;
				}
				
				try {
					Conexion.getInstancia().hacerCambio("INSERT INTO visitas_invitados VALUES(null, " + idInvitado + ", '" + nombre +
							"', '" + apellido + "', '" + telefono + "', '" + direccion + "', '" + sexo + "')");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(AgregadorVisita.this, "Error con la base de datos \nError: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int resultado = JOptionPane.showConfirmDialog(AgregadorVisita.this, "¡Visita agregada correctamente!"
						+ "\n¿Desea agregar otra visita? ",
						"Información", JOptionPane.YES_NO_OPTION);
				switch (resultado) {
				case (JOptionPane.YES_OPTION):
					txtNombre.setText("");
					txtApellido.setText("");
					txtTelefono.setText("");
					txtDireccion.setText("");
					cbSexo.setSelectedIndex(0);
					return;
				case (JOptionPane.NO_OPTION):
					dispose();
					instancia = null;
				break;
				}
			}
		});
		btnAgregarVisita.setBounds(94, 190, 112, 40);
		contentPane.add(btnAgregarVisita);
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				instancia = null;
			}
		});
	}
}
