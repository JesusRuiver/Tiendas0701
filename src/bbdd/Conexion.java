package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Conexion {

	private Connection conexion;
	private String usuario;
	private String contraseña;
	private String baseDatos;
	private String servidor;
	private String conectorJDBC;

	private ResultSet resultado;
	private Statement sentencia;

	private PreparedStatement enviaConsultaArticulosVentas;
	private String consultaPreparadaArticulosVentas = "Select * from ventas where nif=?";
	
	private PreparedStatement enviaConsultaArticulosPedidos;
	private String consultaPreparadaArticulosPedidos = "Select * from pedidos where nif=?";

	
	public Conexion() {

	}

	public Conexion(Connection conexion, String usuario, String contraseña, String baseDatos, String servidor,
			String conectorJDBC) {
		this.conexion = conexion;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.baseDatos = baseDatos;
		this.servidor = servidor;
		this.conectorJDBC = conectorJDBC;
	}

	public void conectar() {

		this.conectorJDBC = "com.mysql.jdbc.Driver";
		this.servidor = "jdbc:mysql://localhost/";
		this.baseDatos = "tiendas";
		this.usuario = "tiendas";
		this.contraseña = "tiendas";

		try {

			Class.forName(getConectorJDBC());

			this.conexion = DriverManager.getConnection(getServidor() + getBaseDatos(), getUsuario(), getContraseña());

			this.sentencia = conexion.createStatement();

			System.out.println("Conectado");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error... No Conectado");
			e.printStackTrace();
		}

	}

	public void consultaPruebas() {

		try {
			// Preparamos la consulta
			Statement sentencia;
			sentencia = conexion.createStatement();

			String sql = "SELECT * FROM fabricantes";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			while (resul.next()) {
				System.out.printf("%d, %s, %s %n", resul.getInt(1), resul.getString(2), resul.getString(3));
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			cerrarConexion(); // Cerrar conexión
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> rellenaComboBoxTiendas() {

		ArrayList<String> lista = new ArrayList<String>();
		String consulta = "SELECT * FROM tiendas";

		try {
			resultado = this.sentencia.executeQuery(consulta);

			System.out.println("Correcto");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error");
		}

		try {
			while (resultado.next()) {

				lista.add("NOMBRE: " + resultado.getString(2) + " CIF: " + resultado.getString(1));
			}
			resultado.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;

	}

	public ArrayList<String> rellenaComboBoxArticulos() {

		ArrayList<String> lista = new ArrayList<String>();
		String consulta = "SELECT * FROM articulos";
		try {
			resultado = this.sentencia.executeQuery(consulta);
			System.out.println("Correcto");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error");
		}

		try {
			while (resultado.next()) {

				lista.add("ARTICULO: " + resultado.getString(1) + " FABRICANTE: " + resultado.getString(2));
			}
			resultado.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;

	}

	public ArrayList<Object[]> rellenaTablaVentas(String nif) {
		
		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		try {
			
			enviaConsultaArticulosVentas = conexion.prepareStatement(consultaPreparadaArticulosVentas);
			enviaConsultaArticulosVentas.setString(1, nif);

			resultado = enviaConsultaArticulosVentas.executeQuery();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultado.next()) {

				Object[] filas = new Object[7];

				for (int i = 0; i < 7; i++) {

					filas[i] = resultado.getObject(i + 1);

				}
				datos.add(filas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;

	}
	
public ArrayList<Object[]> rellenaTablaPedidos(String nif) {
		
		ArrayList<Object[]> datos = new ArrayList<Object[]>();

		try {
			
			enviaConsultaArticulosPedidos = conexion.prepareStatement(consultaPreparadaArticulosPedidos);
			enviaConsultaArticulosPedidos.setString(1, nif);

			resultado = enviaConsultaArticulosPedidos.executeQuery();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultado.next()) {

				Object[] filas = new Object[7];

				for (int i = 0; i < 7; i++) {

					filas[i] = resultado.getObject(i + 1);

				}
				datos.add(filas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;

	}


	public ArrayList<Object[]> rellenaTablaPedidos() {
		ArrayList<Object[]> datos = new ArrayList<Object[]>();
		String consulta = "SELECT * FROM pedidos";

		try {
			resultado = sentencia.executeQuery(consulta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultado.next()) {

				Object[] filas = new Object[7];

				for (int i = 0; i < 7; i++) {

					filas[i] = resultado.getObject(i + 1);

				}
				datos.add(filas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datos;

	}

	public String sumaPrecioCosto() {

		String consulta = "Select SUM(precio_costo) as sumaPrecioCosto from articulos";
		String total = "";
		try {
			resultado = sentencia.executeQuery(consulta);

			if (resultado.next()) {
				total = resultado.getString("sumaPrecioCosto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return total;
	}

	public String sumaPrecioVenta() {

		String consulta = "Select SUM(precio_venta) as sumaVentas from articulos";
		String total = "";
		try {

			resultado = sentencia.executeQuery(consulta);

			if (resultado.next()) {
				total = resultado.getString("sumaVentas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getConectorJDBC() {
		return conectorJDBC;
	}

	public void setConectorJDBC(String conectorJDBC) {
		this.conectorJDBC = conectorJDBC;
	}

}
