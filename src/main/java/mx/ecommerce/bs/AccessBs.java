package mx.ecommerce.bs;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mx.ecommerce.dao.UsuarioDAO;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.util.Correo;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.Validador;

public class AccessBs {

	public static Usuario verificarLogin(String userName, String password) {
		Usuario usuario = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG2", null,
					"userName");
		}
		
		if (Validador.esNuloOVacio(password)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó la contraseña.", "MSG2", null,
					"password");
		}
		
		try {
			usuario = new UsuarioDAO().findById(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuario == null || !usuario.getPassword().equals(password)) {
			throw new ECommerceValidacionException("Usuario no encontrado o contraseña incorrecta", "MSG3");
		}
		return usuario;
		
	}

	public static boolean isLogged(Map<String, Object> userSession) {
		boolean logged = false;
		System.out.println(userSession);
		if (userSession != null) {
			if (userSession.get("login") != null) {
				logged = (Boolean) userSession.get("login");
				System.out.println(logged);
				return logged;
			}
		} 
		return false;
	}

	public static void recuperarContrasenia(String userName) throws AddressException, MessagingException {
		Usuario usuario = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG2", null,
					"userName");
		}
		if (!Validador.esCorreo(userName)) {
			throw new ECommerceValidacionException("Colaborador no encontrado", "MSG4");

		}
		try {
			usuario = new UsuarioDAO().findById(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuario == null) {
			throw new ECommerceValidacionException("Colaborador no encontrado", "MSG4");
		}
		Correo.enviarCorreo(usuario, 1);
		
	}
	
	public static void verificarPermisos(String tmp, Usuario usuario) {
		
	}

}
