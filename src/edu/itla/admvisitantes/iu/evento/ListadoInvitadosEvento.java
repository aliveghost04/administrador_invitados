package edu.itla.admvisitantes.iu.evento;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.iu.AgregadorVisita;
import edu.itla.admvisitantes.iu.AgregadorVisitaImprevista;
import edu.itla.admvisitantes.modelotabla.ModeloTablaInvitadosEvento;

public class ListadoInvitadosEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblListarVisitantesEvento;
	private int idEvento;
	private JButton btnAgregarVisita;
	private int valorBD;
	private JComboBox<String> cbEleccion;
	private JTextField textField;
	private TableRowSorter<TableModel> sorter;

	public ListadoInvitadosEvento(int idEventos, String nombreEvento) {
		
		this.idEvento = idEventos;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Invitados del evento " + nombreEvento);
		setBounds(100, 100, 830, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		tblListarVisitantesEvento = new JTable(new ModeloTablaInvitadosEvento(idEvento));
		sorter = new TableRowSorter<TableModel>(tblListarVisitantesEvento.getModel());
		tblListarVisitantesEvento.setRowSorter(sorter);
		tblListarVisitantesEvento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(tblListarVisitantesEvento);
		scrollPane.setBounds(10, 47, 664, 413);
		contentPane.add(scrollPane);
		
		JLabel lbltrajoVisitas = new JLabel("¿Trajo visitas?");
		lbltrajoVisitas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbltrajoVisitas.setBounds(703, 7, 90, 21);
		contentPane.add(lbltrajoVisitas);
		
		btnAgregarVisita = new JButton("Agregar");
		btnAgregarVisita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = tblListarVisitantesEvento.getSelectedRow();
				if (x == -1) {
					JOptionPane.showMessageDialog(ListadoInvitadosEvento.this, "Debe seleccionar un invitado",
							"Información", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				int y = new ModeloTablaInvitadosEvento(idEvento).getIdEvento(x);
				
				AgregadorVisita.getInstancia(y).setVisible(true);
			}
		});
		btnAgregarVisita.setBounds(704, 44, 89, 45);
		contentPane.add(btnAgregarVisita);
		
		String[] seleccione = { "Nombre", "Apellido" };
		cbEleccion = new JComboBox<String>(seleccione);
		cbEleccion.setBounds(72, 18, 86, 20);
		contentPane.add(cbEleccion);

		textField = new JTextField();
		textField.setBounds(168, 18, 127, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				filtrar(cbEleccion.getSelectedIndex());
			}

			public void insertUpdate(DocumentEvent e) {
				filtrar(cbEleccion.getSelectedIndex());
			}

			public void removeUpdate(DocumentEvent e) {
				filtrar(cbEleccion.getSelectedIndex());
			}
		});

		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setBounds(10, 21, 65, 14);
		contentPane.add(lblBuscarPor);

		JLabel lblvisitaImprevista = new JLabel("¿Visita Imprevista?");
		lblvisitaImprevista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblvisitaImprevista.setBounds(692, 141, 122, 21);
		contentPane.add(lblvisitaImprevista);
		
		JButton btnAgregarImprevisto = new JButton("Agregar");
		btnAgregarImprevisto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AgregadorVisitaImprevista().setVisible(true);
			}
		});
		btnAgregarImprevisto.setBounds(704, 173, 89, 45);
		contentPane.add(btnAgregarImprevisto);
		
		JButton btnRegistrarAsistencia = new JButton("Registrar Asistencia");
		btnRegistrarAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (int x = 0; x < tblListarVisitantesEvento.getRowCount(); x++) {
					if ((boolean) tblListarVisitantesEvento.getValueAt(x, 5) == true) {
						int y = ModeloTablaInvitadosEvento.getIdInvitado(x);
						valorBD = 1;
						try {
							Conexion.getInstancia().hacerCambio("UPDATE invitados "
									+ "SET asistencia_invitado = " + valorBD 
									+ " WHERE id_invitado = " + y);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(ListadoInvitadosEvento.this, "Error con la base de datos",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						int y = ModeloTablaInvitadosEvento.getIdInvitado(x);
						
						valorBD = 0;
						try {
							Conexion.getInstancia().hacerCambio("UPDATE invitados "
									+ "SET asistencia_invitado = " + valorBD 
									+ " WHERE id_invitado = " + y);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(ListadoInvitadosEvento.this, "Error con la base de datos",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				dispose();
			}
		});
		btnRegistrarAsistencia.setBounds(687, 415, 127, 45);
		contentPane.add(btnRegistrarAsistencia);
		
	}
	
	public void filtrar(int x) {
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textField.getText(), x));
	}
	
}
