package main;

import bbdd.Conexion;

public class Main {
	
	public static void main(String[] args) {
		
		Conexion miConexion = new Conexion();
		
		miConexion.conectar();
		
		miConexion.consultaPruebas();

	}

}
