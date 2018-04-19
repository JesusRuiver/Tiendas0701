package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class Ejercicio2 extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private Conexion miConexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio2 frame = new Ejercicio2();
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
	public Ejercicio2() {

		miConexion.conectar();

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(42, 28, 272, 23);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setBounds(109, 62, 109, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(109, 95, 109, 23);
		contentPane.add(rbtnPedidos);

		JButton btnExportarBinarioSecuencial = new JButton("Exportar Fichero Binario");
		btnExportarBinarioSecuencial.setBounds(133, 148, 181, 23);
		contentPane.add(btnExportarBinarioSecuencial);

		JButton btnExportarXML = new JButton("Exportar Fichero XML");
		btnExportarXML.setBounds(133, 193, 181, 23);
		contentPane.add(btnExportarXML);

		JButton btnImportarBinarioSecuencial = new JButton("Importar Fichero Binario");
		btnImportarBinarioSecuencial.setBounds(133, 240, 181, 23);
		contentPane.add(btnImportarBinarioSecuencial);

		JButton btnImportarFicheroXML = new JButton("Importar Fichero XML");
		btnImportarFicheroXML.setBounds(133, 292, 181, 23);
		contentPane.add(btnImportarFicheroXML);

		// Primero rellenamos el comboBox de Tiendas con NIF
		
		rellenaComboBox(cboxTiendas);
		
		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/
		
		
		

	}

	/*-------------------------------------METODOS-----------------------------------*/

	private void rellenaComboBox(JComboBox cboxTiendas) {
		ArrayList<String> lista = new ArrayList<String>();

		lista = miConexion.rellenaComboBoxTiendas();

		for (int i = 0; i < lista.size(); i++) {
			cboxTiendas.addItem(lista.get(i));
		}
	}
}
