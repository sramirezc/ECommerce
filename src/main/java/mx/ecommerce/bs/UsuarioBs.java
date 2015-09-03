package mx.ecommerce.bs;

import mx.ecommerce.bs.PerfilBs.Perfil;
import mx.ecommerce.dao.UsuarioDAO;
import mx.ecommerce.model.Usuario;

public class UsuarioBs {
	public static boolean isAdministrador(Usuario usuario) {
		if (usuario.getPerfil().getId() == PerfilBs.getId(Perfil.ADMINISTRADOR)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isCliente(Usuario usuario) {
		if (usuario.getPerfil().getId() == PerfilBs.getId(Perfil.CLIENTE)) {
			return true;
		} else {
			return false;
		}	}
	
	public static boolean isAlmacen(Usuario usuario) {
		if (usuario.getPerfil().getId() == PerfilBs.getId(Perfil.ALMACEN)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Usuario findById(String correo) {
		return new UsuarioDAO().findById(correo);
	}
}
