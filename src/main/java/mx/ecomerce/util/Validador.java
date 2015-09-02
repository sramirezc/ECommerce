package mx.ecomerce.util;

import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validador {

	public static boolean esNuloOVacio(String cadena) {
		return cadena == null || cadena.equals("");
	}

	public static boolean esNulo(Object objeto) {
		return objeto == null;
	}

	public static boolean validaLongitudMaxima(String clave, int i) {
		return clave.length() > i;
	}

	public static boolean esNuloOVacio(Set set) {
		return set == null || set.size() == 0;
	}

	public static boolean contieneCaracterInvalido(String cadena) {
		return cadena.contains("_") || cadena.contains(":")
				|| cadena.contains("·") || cadena.contains(".")
				|| cadena.contains(",");
	}

	public static boolean esCorreo(String userName) {
		boolean correo = true;
		try {
			InternetAddress emailAddr = new InternetAddress(userName);
			emailAddr.validate();
		} catch (AddressException ex) {
			correo = false;
		}
		return correo;

	}

}
