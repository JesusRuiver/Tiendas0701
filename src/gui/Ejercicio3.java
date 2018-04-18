package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class Ejercicio3 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	private Conexion miConexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio3 frame = new Ejercicio3();
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
	public Ejercicio3() {
		
		miConexion.conectar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 34, 294, 308);
		contentPane.add(scrollPane);
		
		JList listTiendas = new JList();
		scrollPane.setViewportView(listTiendas);
		
		JComboBox cboxArticulos = new JComboBox();
		cboxArticulos.setBounds(379, 34, 267, 20);
		contentPane.add(cboxArticulos);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(539, 91, 107, 20);
		contentPane.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(539, 153, 107, 20);
		contentPane.add(spinner_1);
		
		textField = new JTextField();
		textField.setBounds(539, 223, 107, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(539, 273, 107, 20);
		contentPane.add(textField_1);
		
		JLabel lbPeso = new JLabel("Peso");
		lbPeso.setBounds(447, 94, 46, 14);
		contentPane.add(lbPeso);
		
		JLabel lbunidades = new JLabel("Unidades");
		lbunidades.setBounds(447, 156, 46, 14);
		contentPane.add(lbunidades);
		
		JLabel lbFecha = new JLabel("Fecha");
		lbFecha.setBounds(447, 226, 46, 14);
		contentPane.add(lbFecha);
		
		JLabel lbCategoria = new JLabel("Categoria");
		lbCategoria.setBounds(445, 276, 64, 14);
		contentPane.add(lbCategoria);
		
		JButton btnInsertarArticulo = new JButton("Insertar");
		btnInsertarArticulo.setBounds(557, 327, 89, 23);
		contentPane.add(btnInsertarArticulo);
		
		rellenaLista(listTiendas);
		
		ArrayList<String> articulos = new ArrayList<String>();
		
		articulos = miConexion.rellenaComboBoxArticulos();
		
		for(int i = 0; i < articulos.size(); i++){
			
			cboxArticulos.addItem(articulos.get(i));
		}
		
	}

	private void rellenaLista(JList listTiendas) {
		DefaultListModel<String> modeloLista = new DefaultListModel<String>();
		
		ArrayList<String> datos = new ArrayList<String>();
		
		datos = miConexion.rellenaComboBoxTiendas();
		
		for (int i = 0; i<datos.size();i++){
			modeloLista.addElement(datos.get(i));
		}
		
		listTiendas.setModel(modeloLista);
	}
}
