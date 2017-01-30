package edu.itla.admvisitantes.iu.invitado;

import java.awt.Color;
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
import javax.swing.border.LineBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.modelotabla.Invitado;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventos;
import edu.itla.admvisitantes.modelotabla.ModeloTablaInvitados;

public class MantenimientoInvitados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableInvitados;
	private JButton btnAgregarInvitado;
	private JButton btnEliminarInvitado;
	private JButton btnModificarInvitado;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblTelefono;
	private JLabel lblDireccion;
	private JLabel lblSexo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JPanel panel;
	private JLabel lblAnadirInvitados;
	private JComboBox<String> cbSexo;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String sexo;
	private static MantenimientoInvitados instancia;
	private JLabel lblEvento;
	private String evento;
	private int idEvento;
	private JButton btnSeleccionar;
	private JScrollPane contenedorTabla;
	private JTable tblEventos;
	
	public static MantenimientoInvitados getInstancia() {
		
		if (instancia == null) {
			instancia = new MantenimientoInvitados();
		}
		return instancia;
	}
	
	public int getFilaSeleccionada() {
		return tableInvitados.getSelectedRow();
	}
	
	private MantenimientoInvitados() {
		
		setTitle("Mantenimiento de Invitados");
		setBounds(100, 100, 600, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		tableInvitados = new JTable(ModeloTablaInvitados.getInstancia());
		
		JScrollPane scrollPane = new JScrollPane(tableInvitados);
		scrollPane.setBounds(10, 11, 574, 250);
		contentPane.add(scrollPane);
		
		btnEliminarInvitado = new JButton("Eliminar Invitado");
		btnEliminarInvitado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableInvitados.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Debe seleccionar un invitado", 
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					int resultado = JOptionPane.showConfirmDialog(MantenimientoInvitados.this , "¿Desea realmente elimiar el contacto?",
							"Pregunta", JOptionPane.YES_NO_OPTION);
					if (resultado == JOptionPane.YES_OPTION) {
						ModeloTablaInvitados.getInstancia().eliminar(tableInvitados.getSelectedRow());
					} 
				}
			}
		});
		btnEliminarInvitado.setBounds(10, 272, 127, 23);
		contentPane.add(btnEliminarInvitado);
		
		btnModificarInvitado = new JButton("Modificar Invitado");
		btnModificarInvitado.setBounds(457, 272, 127, 23);
		btnModificarInvitado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(tableInvitados.getSelectedRow() == -1){
				JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Debe seleccionar un invitado",
													"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}else{
					ModificarInvitado.getInstancia().setVisible(true);}
				}
		});
		contentPane.add(btnModificarInvitado);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(0, 303, 584, 177);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 28, 97, 14);
		panel.add(lblNombre);
		
		lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(20, 53, 46, 14);
		panel.add(lblApellido);
		
		lblTelefono = new JLabel("Teléfono: ");
		lblTelefono.setBounds(20, 78, 56, 14);
		panel.add(lblTelefono);
		
		lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(20, 103, 56, 14);
		panel.add(lblDireccion);
		
		lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(20, 130, 46, 14);
		panel.add(lblSexo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(69, 25, 170, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		RestrictedTextField restriccionTxtNombre = new RestrictedTextField(txtNombre);
		restriccionTxtNombre.setLimit(30);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(69, 50, 170, 20);
		panel.add(txtApellido);
		txtApellido.setColumns(10);
		RestrictedTextField restriccionTxtApellido = new RestrictedTextField(txtApellido);
		restriccionTxtApellido.setLimit(30);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(69, 75, 170, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		RestrictedTextField restriccionTxtTelefono = new RestrictedTextField(txtTelefono);
		restriccionTxtTelefono.setLimit(10);
		restriccionTxtTelefono.setOnlyNums(true);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(69, 100, 170, 20);
		panel.add(txtDireccion);
		txtDireccion.setColumns(10);
		RestrictedTextField restriccionTxtDireccion = new RestrictedTextField(txtDireccion);
		restriccionTxtDireccion.setLimit(70);
		
		String[] tipoSexo = {"Seleccione", "M", "F"};
		cbSexo = new JComboBox<String>(tipoSexo);
		cbSexo.setBounds(69, 127, 75, 20);
		panel.add(cbSexo);
		
		tblEventos = new JTable(ModeloTablaEventos.getInstancia());
		contenedorTabla = new JScrollPane(tblEventos);
		
		btnSeleccionar = new JButton("Seleccionar...");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int resultado = JOptionPane.showConfirmDialog(null, contenedorTabla, 
						"Seleccione un evento", JOptionPane.OK_CANCEL_OPTION);
				switch (resultado) {
				case (JOptionPane.OK_OPTION):
					idEvento = ModeloTablaEventos.getInstancia().getIdEvento(tblEventos.getSelectedRow());
				btnSeleccionar.setText("" + idEvento);
				break;
				case (JOptionPane.CANCEL_OPTION):
					return;
				}
			}
		});
		btnSeleccionar.setBounds(319, 24, 140, 21);
		panel.add(btnSeleccionar);
		
		btnAgregarInvitado = new JButton("Agregar Invitado");
		btnAgregarInvitado.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				nombre = txtNombre.getText();
				apellido = txtApellido.getText();
				direccion = txtDireccion.getText();
				telefono = txtTelefono.getText();
				
				if (nombre.equals("") || apellido.equals("") || direccion.equals("")) {
					JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Debe llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} 
				
				if (idEvento == 0) {
					JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Debe seleccionar un evento",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					try {
						ResultSet rs = Conexion.getInstancia().hacerConsulta("SELECT nombre_evento FROM eventos WHERE id_evento = " + idEvento);
						if (rs.next()) {
							evento = rs.getString(1);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Hay un error con la base de datos",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				try {
					Long.parseLong(telefono);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Introduzca un número válido", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				switch (cbSexo.getSelectedIndex()) {
					case (0):
						JOptionPane.showMessageDialog(MantenimientoInvitados.this, "Debe seleccionar el sexo", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					case (1):
						sexo = String.valueOf((cbSexo.getSelectedItem()));
					break;
					case (2):
						sexo = String.valueOf((cbSexo.getSelectedItem()));
					break;
				}
				
				try {
					Conexion.getInstancia().hacerCambio("INSERT INTO invitados VALUES (null, " + idEvento +
							", '" + nombre + "', '" + apellido + "', '" + telefono + "', '" + direccion + "', '" + sexo + "', 0)");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MantenimientoInvitados.this, "No se pudo guardar en la base de datos " +
							e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			
				Invitado invitado = new Invitado(idEvento, nombre, apellido, telefono, direccion, sexo, evento, false);
				ModeloTablaInvitados.getInstancia().agregar(invitado);
			
				txtNombre.setText("");
				txtApellido.setText("");
				txtTelefono.setText("");
				txtDireccion.setText("");
				cbSexo.setSelectedIndex(0);
				
			}
		});
		btnAgregarInvitado.setBounds(219, 141, 127, 25);
		panel.add(btnAgregarInvitado);
		
		lblAnadirInvitados = new JLabel("Añadir Invitados");
		lblAnadirInvitados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnadirInvitados.setBounds(229, 0, 117, 25);
		panel.add(lblAnadirInvitados);
		
		lblEvento = new JLabel("Evento:");
		lblEvento.setBounds(263, 28, 46, 14);
		panel.add(lblEvento);
		
		addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
				instancia = null;
			}
		});
		
	}
}
