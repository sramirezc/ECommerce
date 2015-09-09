package mx.ecommerce.bs;

import java.util.List;
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
		List<Usuario> usuarios = null;
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
			usuarios = new UsuarioDAO().findByCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuarios == null || usuarios.isEmpty() || !usuarios.get(0).getPassword().equals(password)) {
			throw new ECommerceValidacionException("Usuario no encontrado o contraseña incorrecta", "MSG3");
		}
		return usuarios.get(0);
		
	}

	public static boolean isLogged(Map<String, Object> userSession) {
		boolean logged = false;
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
		List<Usuario> usuarios = null;

		if (Validador.esNuloOVacio(userName)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el correo electrónico", "MSG2", null,
					"userName");
		}
		if (!Validador.esCorreo(userName)) {
			throw new ECommerceValidacionException("Usuario no encontrado", "MSG4");

		}
		try {
			usuarios = new UsuarioDAO().findByCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuarios == null || usuarios.isEmpty()) {
			throw new ECommerceValidacionException("Colaborador no encontrado", "MSG4");
		}

		Correo.enviarCorreo(usuarios.get(0), 1);
		
		
	}
	
	public static String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}

	public static void showErrorCaptcha() {
		throw new ECommerceValidacionException("Captcha incorrecto", "MSG15");
		
	}

	public static boolean verificarCaptcha(String captcha, String hash) {
		if (Validador.esNuloOVacio(captcha)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el captcha", "MSG15", null,
					"captcha");
		}
		if (!rpHash(captcha).equals(hash)) {
			throw new ECommerceValidacionException(
					"El usuario ingresó el captcha incorrecto", "MSG15", null,
					"captcha");
		}
		return false;
	}

}
