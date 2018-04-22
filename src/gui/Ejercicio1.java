package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bbdd.Conexion;

public class Ejercicio1 extends JFrame {

	private JPanel contentPane;
	private JTable tablaVentas;
	private JTable tablaPedidos;
	private JLabel lbResultadoTotal = new JLabel("");

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton rbtnVentas = new JRadioButton("Ventas");
	private JRadioButton rbtnPedidos = new JRadioButton("Pedidos");

	private Conexion miConexion = new Conexion();

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
	 * Create the frame. //Constructor de la Ventana
	 */
	public Ejercicio1() {

		miConexion.conectar();

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 586);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> cboxTiendas = new JComboBox<String>();
		cboxTiendas.setBounds(33, 22, 262, 26);
		contentPane.add(cboxTiendas);

		buttonGroup.add(rbtnVentas);

		rbtnVentas.setBounds(65, 66, 77, 23);
		contentPane.add(rbtnVentas);

		buttonGroup.add(rbtnPedidos);

		rbtnPedidos.setBounds(65, 92, 77, 23);
		contentPane.add(rbtnPedidos);

		JLabel lbTotal = new JLabel("Total:");
		lbTotal.setFont(new Font("Arial", Font.BOLD, 15));
		lbTotal.setBounds(330, 512, 46, 14);
		contentPane.add(lbTotal);
		lbResultadoTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lbResultadoTotal.setBounds(378, 480, 351, 14);
		contentPane.add(lbResultadoTotal);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 127, 775, 353);
		contentPane.add(scrollPane);

		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/

		// Primero rellenamos el comboBox de Tiendas con NIF

		rellenaComboBox(cboxTiendas);

		// Antes de accionar el combobox

		// Inicializamos en radiobuton de ventas seleccionado y construimos la tabla de ventas

		rbtnVentas.setSelected(true);

		String nif = troceaNIF(cboxTiendas);

		DefaultTableModel modelo = construyeModeloTablaArticulosVentas(scrollPane);

		rellenaTablaArticulosVentasSeleccionandoNIF(modelo, nif);

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = troceaNIF(cboxTiendas);

				DefaultTableModel modelo = construyeModeloTablaArticulosVentas(scrollPane);

				rellenaTablaArticulosVentasSeleccionandoNIF(modelo, nif);

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nif = troceaNIF(cboxTiendas);

				DefaultTableModel modelo = construyeModeloTablaArticulosPedidos(scrollPane);

				rellenaTablaArticulosPedidosSeleccionandoNIF(modelo, nif);

			}
		});

		// Accion a la hora de seleccionar en el comboBox

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (rbtnVentas.isSelected() == true) {

					accionComboBoxVentasSeleccionado(cboxTiendas, scrollPane);

				} else {

					accionComboBoxPedidosSeleccionado(cboxTiendas, scrollPane);
				}

			}

		});

	}

	/*-------------------------------------METODOS-----------------------------------*/

	private DefaultTableModel construyeModeloTablaArticulosVentas(JScrollPane scrollPane) {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("NIF");
		modelo.addColumn("ARTICULO");
		modelo.addColumn("FABRICANTE");
		modelo.addColumn("PESO");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("FECHA VENTA");
		modelo.addColumn("UNIDADES");

		tablaVentas = new JTable(modelo);
		scrollPane.setViewportView(tablaVentas);

		return modelo;
	}

	private DefaultTableModel construyeModeloTablaArticulosPedidos(JScrollPane scrollPane) {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("NIF");
		modelo.addColumn("ARTICULO");
		modelo.addColumn("FABRICANTE");
		modelo.addColumn("PESO");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("FECHA PEDIDO");
		modelo.addColumn("UNIDADES");

		tablaPedidos = new JTable(modelo);
		scrollPane.setViewportView(tablaPedidos);

		return modelo;
	}

	private void rellenaTablaArticulosVentasSeleccionandoNIF(DefaultTableModel modelo, String nif) {

		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		datos = miConexion.rellenaTablaVentas(nif);

		for (int i = 0; i < datos.size(); i++) {
			modelo.addRow(datos.get(i));
		}
	}

	private void rellenaTablaArticulosPedidosSeleccionandoNIF(DefaultTableModel modelo, String nif) {

		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		datos = miConexion.rellenaTablaPedidos(nif);

		for (int i = 0; i < datos.size(); i++) {
			modelo.addRow(datos.get(i));
		}
	}

	public String troceaNIF(JComboBox cboxTiendas) {

		String tiendaYnif;

		tiendaYnif = cboxTiendas.getSelectedItem().toString().trim();

		String[] parteNif = tiendaYnif.trim().split(": ");

		String nif = parteNif[2];

		return nif;
	}

	public void rellenaComboBox(JComboBox<String> cboxTiendas) {
		ArrayList<String> lista = new ArrayList<String>();

		lista = miConexion.rellenaComboBoxTiendas();

		for (int i = 0; i < lista.size(); i++) {
			cboxTiendas.addItem(lista.get(i));
		}
	}

	/**
	 * Acción del ComboBox, trocea el String para obtener un nif y se lo pasa a una
	 * consulta preparada para optener articulos por nif de tienda
	 * 
	 * @param cboxTiendas
	 * @param scrollPane
	 */
	private void accionComboBoxVentasSeleccionado(JComboBox<String> cboxTiendas, JScrollPane scrollPane) {
		// Creamos un modelos contruyendolo a traves de nuestro metodo
		// construyeModeloTablaArticulos

		DefaultTableModel modelo = construyeModeloTablaArticulosVentas(scrollPane);

		modelo.setRowCount(0); // Borra lo que hay en la tabla

		// Almacenamos el string obtenido de nuestro metodo troceaNIF

		String nif = troceaNIF(cboxTiendas).trim();

		// Rellenamos la tabla pasandole el modelo y el string almacenado

		rellenaTablaArticulosVentasSeleccionandoNIF(modelo, nif);

	}

	private void accionComboBoxPedidosSeleccionado(JComboBox<String> cboxTiendas, JScrollPane scrollPane) {
		// Creamos un modelos contruyendolo a traves de nuestro metodo
		// construyeModeloTablaArticulos

		DefaultTableModel modelo = construyeModeloTablaArticulosPedidos(scrollPane);

		modelo.setRowCount(0); // Borra lo que hay en la tabla

		// Almacenamos el string obtenido de nuestro metodo troceaNIF

		String nif = troceaNIF(cboxTiendas).trim();

		// Rellenamos la tabla pasandole el modelo y el string almacenado

		rellenaTablaArticulosPedidosSeleccionandoNIF(modelo, nif);

	}

}
