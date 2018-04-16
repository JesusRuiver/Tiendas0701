package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Conexion {

	private Connection conexion;
	private String usuario;
	private String contrase�a;
	private String baseDatos;
	private String servidor;
	private String conectorJDBC;

	private ResultSet resultado;
	private Statement sentencia;

	public Conexion() {

	}

	public Conexion(Connection conexion, String usuario, String contrase�a, String baseDatos, String servidor,
			String conectorJDBC) {
		this.conexion = conexion;
		this.usuario = usuario;
		this.contrase�a = contrase�a;
		this.baseDatos = baseDatos;
		this.servidor = servidor;
		this.conectorJDBC = conectorJDBC;
	}

	public void conectar() {

		this.conectorJDBC = "com.mysql.jdbc.Driver";
		this.servidor = "jdbc:mysql://localhost/";
		this.baseDatos = "tiendas";
		this.usuario = "tiendas";
		this.contrase�a = "tiendas";

		try {

			Class.forName(getConectorJDBC());

			this.conexion = DriverManager.getConnection(getServidor() + getBaseDatos(), getUsuario(), getContrase�a());

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
			cerrarConexion(); // Cerrar conexi�n
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

	public ArrayList<String> rellenaComboBox() {

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

	public ArrayList<Object[]> rellenaTabla() {
		ArrayList<Object[]> datos = new ArrayList<Object[]>();
		String consulta = "SELECT * FROM ARTICULOS";

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
			
			if(resultado.next()){
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

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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