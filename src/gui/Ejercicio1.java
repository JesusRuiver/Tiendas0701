package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bbdd.Conexion;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ejercicio1 extends JFrame {

	private JPanel contentPane;
	private JTable tablaArticulos;
	private JLabel lbResultadoTotal = new JLabel("");
	private Conexion miConexion = new Conexion();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio1 frame = new Ejercicio1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public Ejercicio1() {

		miConexion.conectar();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(33, 22, 262, 26);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String total = miConexion.sumaPrecioVenta();

				lbResultadoTotal.setText(total + " Precio Venta al Publico");

			}
		});
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setBounds(65, 66, 77, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String total = miConexion.sumaPrecioCosto();

				lbResultadoTotal.setText(total + " Precio de costo");
			}
		});
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(65, 92, 77, 23);
		contentPane.add(rbtnPedidos);

		JLabel lbTotal = new JLabel("Total:");
		lbTotal.setFont(new Font("Arial", Font.BOLD, 15));
		lbTotal.setBounds(325, 480, 46, 14);
		contentPane.add(lbTotal);
		lbResultadoTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lbResultadoTotal.setBounds(378, 480, 351, 14);
		contentPane.add(lbResultadoTotal);

		rellenaTablaArticulos();

		rellenaComboBox(cboxTiendas);

	}

	public void rellenaTablaArticulos() {
		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("ARTICULO");
		modelo.addColumn("FABRICANTE");
		modelo.addColumn("PESO");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("PRECIO");
		modelo.addColumn("PRECIO COSTO");
		modelo.addColumn("EXISTENCIAS");

		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		datos = miConexion.rellenaTabla();

		for (int i = 0; i < datos.size(); i++) {

			modelo.addRow(datos.get(i));
		}

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(34, 139, 695, 313);
		contentPane.add(scrollPane_1);

		tablaArticulos = new JTable();
		scrollPane_1.setViewportView(tablaArticulos);

		this.tablaArticulos.setModel(modelo);
	}

	public void rellenaComboBox(JComboBox cboxTiendas) {
		ArrayList<String> lista = new ArrayList<String>();

		lista = miConexion.rellenaComboBox();

		for (int i = 0; i < lista.size(); i++) {
			cboxTiendas.addItem(lista.get(i));
		}
	}
}
