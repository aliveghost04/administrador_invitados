package edu.itla.admvisitantes.iu.invitado;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import edu.itla.admvisitantes.modelotabla.ModeloTablaFiltro;

public class ListadoInvitados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	TableRowSorter<TableModel> sorter;
	private JTextField textField;
	private JComboBox<String> cbEleccion;
	private static ListadoInvitados instancia;
	
	public static ListadoInvitados getInstancia() {
		
		if (instancia == null) {
			instancia = new ListadoInvitados();
		}
		return instancia;
	}

	private ListadoInvitados() {

		setTitle("Listar Invitados");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 705, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		table = new JTable(new ModeloTablaFiltro());

		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 46, 669, 354);
		contentPane.add(scrollPane);

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

	}

	public void filtrar(int x) {
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textField.getText(), x));
	}
}
