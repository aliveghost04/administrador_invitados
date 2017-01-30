package edu.itla.admvisitantes.iu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.itla.admvisitantes.bd.Conexion;
import edu.itla.admvisitantes.iu.evento.ListadoInvitadosEvento;
import edu.itla.admvisitantes.iu.evento.MantenimientoEventos;
import edu.itla.admvisitantes.iu.invitado.ListadoInvitados;
import edu.itla.admvisitantes.iu.invitado.MantenimientoInvitados;
import edu.itla.admvisitantes.iu.login.Login;
import edu.itla.admvisitantes.iu.usuario.MantenimientoUsuarios;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventosHoy;
import edu.itla.admvisitantes.modelotabla.ModeloTablaEventosProximos;

public class InterfazUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JTable tableEventos;
	private JLabel lblUsuarioConectado;
	private ResultSet rs;
	private String nombreBD;
	private String apellidoBD;
	private String cargo;
	private JTable tblEventosProximos;
	private int idEvento;
	private String nombreEvento;

	public InterfazUsuario(String usuario) {
		
		setTitle("Administrador de Visitantes");
		setSize(800, 440);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		AgregadorVisitaImprevista.setAutorizante(usuario);
		
		lblUsuarioConectado = new JLabel();
		lblUsuarioConectado.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
		lblUsuarioConectado.setBounds(29, 354, 535, 25);
		lblUsuarioConectado.setForeground(Color.BLUE);
		contentPane.add(lblUsuarioConectado);
		
		try {
			rs = Conexion.getInstancia().hacerConsulta("SELECT u.nombre_usuario, u.apellido_usuario,"
					+ " c.nombre_cargo from usuarios u JOIN cargos c using (id_cargo) "
					+ "where usuario = '" + usuario + "'");
			while (rs.next()) {
				nombreBD = rs.getString("nombre_usuario");
				apellidoBD = rs.getString("apellido_usuario");
				cargo = rs.getString("nombre_cargo");
				nombreBD += " " + apellidoBD + " (" + cargo + ")";
			}
			lblUsuarioConectado.setText("Usted esta conectado como " + nombreBD);
		} catch (Exception e) {
			lblUsuarioConectado.setText("");
		}
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		
		JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
		mntmCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Login().setVisible(true);
				dispose();
			}
		});
		mnArchivo.add(mntmCerrarSesion);
		
		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);
		mnArchivo.add(mntmSalir);
		
		JMenu mnVisitantes = new JMenu("Invitados");
		menuBar.add(mnVisitantes);
		
		JMenuItem mntmListadoDeVisitantes = new JMenuItem("Listado de Invitados");
		mntmListadoDeVisitantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListadoInvitados.getInstancia().setVisible(true);
			}
		});
		mnVisitantes.add(mntmListadoDeVisitantes);
		
		tableEventos = new JTable(ModeloTablaEventosHoy.getInstancia());
		
		JScrollPane scrollPane = new JScrollPane(tableEventos);
		scrollPane.setBounds(29, 40, 434, 261);
		contentPane.add(scrollPane);
		
		JLabel lblEventosDeHoy = new JLabel("Eventos de Hoy");
		lblEventosDeHoy.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventosDeHoy.setForeground(Color.BLACK);
		lblEventosDeHoy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEventosDeHoy.setBounds(29, 4, 434, 25);
		contentPane.add(lblEventosDeHoy);
		
		JButton btnAbrirEvento = new JButton("Abrir Evento");
		btnAbrirEvento.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableEventos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(InterfazUsuario.this, "Debe Seleccionar un Evento",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					new AccionAbrirEvento().actionPerformed(null);
				}
			}
			
		});
		btnAbrirEvento.setBounds(29, 312, 93, 23);
		contentPane.add(btnAbrirEvento);
		
		JLabel lblEventosPrximos = new JLabel("Eventos Próximos");
		lblEventosPrximos.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventosPrximos.setForeground(Color.BLACK);
		lblEventosPrximos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEventosPrximos.setBounds(483, 4, 284, 25);
		contentPane.add(lblEventosPrximos);
		
		tblEventosProximos = new JTable(new ModeloTablaEventosProximos());
		tblEventosProximos.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (tblEventosProximos.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(InterfazUsuario.this, "Debe Seleccionar un Evento",
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					String[] x = new ModeloTablaEventosProximos().informacion(tblEventosProximos.getSelectedRow());
					JOptionPane.showMessageDialog(InterfazUsuario.this, x, 
							"Información del evento", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JScrollPane spContenedorTablaProxEventos = new JScrollPane(tblEventosProximos);
		spContenedorTablaProxEventos.setBounds(483, 40, 284, 261);
		contentPane.add(spContenedorTablaProxEventos);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					Conexion.getInstancia().cerrarConexion();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(InterfazUsuario.this, "No se puede cerrar la Conexion",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}

	public void asignarAdministrador() {

		JMenu mnMantenimiento = new JMenu("Mantenimiento");
		JMenu mnReporte = new JMenu("Reporte");
		
		JMenuItem mntmReporte = new JMenuItem("Reportes");
		mntmReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reportes.getInstancia().setVisible(true);;
			}
		});

		mnReporte.add(mntmReporte);
		JMenuItem mntmMantenimientoUsuarios = new JMenuItem(
				"Mantenimiento de Usuarios");
		mntmMantenimientoUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MantenimientoUsuarios.getInstancia().setVisible(true);
			}
		});
		mnMantenimiento.add(mntmMantenimientoUsuarios);

		JMenuItem mntmMantenimientoDeEventos = new JMenuItem(
				"Mantenimiento de Eventos");
		mntmMantenimientoDeEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MantenimientoEventos.getInstancia().setVisible(true);
			}
		});
		mnMantenimiento.add(mntmMantenimientoDeEventos);

		JMenuItem mntmMantenimientoInvitados = new JMenuItem(
				"Mantenimiento de Invitados");
		mntmMantenimientoInvitados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MantenimientoInvitados.getInstancia().setVisible(true);
			}
		});
		mnMantenimiento.add(mntmMantenimientoInvitados);

		menuBar.add(mnMantenimiento);
		menuBar.add(mnReporte);
		setVisible(true);
	}
	
	private class AccionAbrirEvento implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			idEvento = ModeloTablaEventosHoy.getInstancia().
					verificar(tableEventos.getSelectedRow());
			
			try {
				ResultSet rs = Conexion.getInstancia().hacerConsulta("SELECT nombre_evento FROM eventos "
						+ "WHERE id_evento = '" + idEvento + "'");
				if (rs.next()) {
					nombreEvento = rs.getString(1);
				}
				new ListadoInvitadosEvento(idEvento, nombreEvento).setVisible(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(InterfazUsuario.this, "Error con la base de datos", 
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
