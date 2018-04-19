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
	private JTable tablaArticulos;
	private JLabel lbResultadoTotal = new JLabel("");

	private final ButtonGroup buttonGroup = new ButtonGroup();

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

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		rbtnVentas.setSelected(true);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");

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

		// Accion a la hora de seleccionar en el comboBox

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				accionComboBox(cboxTiendas, scrollPane);

			}

		});

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String total = miConexion.sumaPrecioVenta();

				lbResultadoTotal.setText(total + " Precio Venta al Publico");

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String total = miConexion.sumaPrecioCosto();

				lbResultadoTotal.setText(total + " Precio de costo");
			}
		});

	}

	/*-------------------------------------METODOS-----------------------------------*/

	private DefaultTableModel construyeModeloTablaArticulos(JScrollPane scrollPane) {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("NIF");
		modelo.addColumn("ARTICULO");
		modelo.addColumn("FABRICANTE");
		modelo.addColumn("PESO");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("FECHA DE VENTA");
		modelo.addColumn("UNIDADES");

		tablaArticulos = new JTable(modelo);
		scrollPane.setViewportView(tablaArticulos);

		return modelo;
	}

	private void rellenaTablaArticulosSeleccionandoNIF(DefaultTableModel modelo, String nif) {

		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		datos = miConexion.rellenaTablaVentas(nif);

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
	 * Acción del ComboBox, trocea el String para obtener un nif y se lo pasa a
	 * una consulta preparada para optener articulos por nif de tienda
	 * 
	 * @param cboxTiendas
	 * @param scrollPane
	 */
	private void accionComboBox(JComboBox<String> cboxTiendas, JScrollPane scrollPane) {
		// Creamos un modelos contruyendolo a traves de nuestro metodo
		// construyeModeloTablaArticulos

		DefaultTableModel modelo = construyeModeloTablaArticulos(scrollPane);

		modelo.setRowCount(0); // Borra lo que hay en la tabla

		// Almacenamos el string obtenido de nuestro metodo troceaNIF

		String nif = troceaNIF(cboxTiendas).trim();

		// Rellenamos la tabla pasandole el modelo y el strig almacenado

		rellenaTablaArticulosSeleccionandoNIF(modelo, nif);
	}
}
