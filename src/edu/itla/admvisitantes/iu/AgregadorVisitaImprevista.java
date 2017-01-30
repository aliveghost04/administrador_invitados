package edu.itla.admvisitantes.iu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import edu.itla.admvisitantes.bd.Conexion;

public class AgregadorVisitaImprevista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JLabel lblCantidadCaracteres;
	private JLabel lblExcedidoLimite;
	private String nombre;
	private String apellido;
	private String razon;
	private static String autorizante;
	private JTextField txtRazon;
	
	public static void setAutorizante(String usuario) {
		AgregadorVisitaImprevista.autorizante = usuario;
	}

	public AgregadorVisitaImprevista() {
		
		setTitle("Agregar Visita Imprevista");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		lblExcedidoLimite = new JLabel("*Solo se acepta un máximo de 100 caracteres");
		lblExcedidoLimite.setForeground(Color.red);
		lblExcedidoLimite.setVisible(false);
		lblExcedidoLimite.setBounds(10, 166, 280, 14);
		contentPane.add(lblExcedidoLimite);
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 13);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 30, 67, 14);
		lblNombre.setFont(fuente);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(10, 65, 67, 14);
		lblApellido.setFont(fuente);
		contentPane.add(lblApellido);
		
		JLabel lblRazon = new JLabel("Razón de entrada:");
		lblRazon.setBounds(10, 97, 128, 14);
		lblRazon.setFont(fuente);
		contentPane.add(lblRazon);
		
		lblCantidadCaracteres = new JLabel();
		lblCantidadCaracteres.setBounds(285, 166, 20, 14);
		contentPane.add(lblCantidadCaracteres);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(77, 28, 160, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(30);
		
		txtRazon = new JTextField();
		txtRazon.setText("Escriba la razón aquí");
		txtRazon.setBounds(10, 122, 401, 23);
		contentPane.add(txtRazon);
		txtRazon.selectAll();
		txtRazon.setColumns(10);
		RestrictedTextField restriccionTxtRazon = new RestrictedTextField(txtRazon);
		restriccionTxtRazon.setLimit(100);
		txtRazon.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int x = txtRazon.getText().length();
				lblCantidadCaracteres.setText("" + x);
				
			}
			
			public void keyPressed(KeyEvent e) {
				int x = txtRazon.getText().length();
				if (x == 100) {
					lblExcedidoLimite.setVisible(true);
				} else {
					lblExcedidoLimite.setVisible(false);
				}
			}
		});
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(77, 63, 160, 20);
		contentPane.add(txtApellido);
		RestrictedTextField restriccionTxtApellido = new RestrictedTextField(txtApellido);
		restriccionTxtApellido.setLimit(30);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nombre = txtNombre.getText();
				apellido = txtApellido.getText();
				razon = txtRazon.getText();
				
				try {
					Conexion.getInstancia().hacerCambio("INSERT INTO visitas_imprevistas VALUES(null, '" + nombre + "', '" + apellido +"', '" + razon + "', '" + autorizante + "')");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(AgregadorVisitaImprevista.this, "Error con la base de datos",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				txtNombre.setText("");
				txtApellido.setText("");
				txtRazon.setText("Escriba la razón aquí");
				txtRazon.selectAll();
				
			}
		});
		btnGuardar.setBounds(322, 162, 89, 23);
		contentPane.add(btnGuardar);
		
	}
}
