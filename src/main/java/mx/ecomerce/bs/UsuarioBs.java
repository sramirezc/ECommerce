package mx.ecomerce.bs;

import mx.ecomerce.model.Usuario;

public class UsuarioBs {
	public static boolean isAdministrador(Usuario usuario) {
		return false;
	}
	
	public static boolean isCliente(Usuario usuario) {
		return false;
	}
	
	public static boolean isAlmacen(Usuario usuario) {
		return true;
	}
}
