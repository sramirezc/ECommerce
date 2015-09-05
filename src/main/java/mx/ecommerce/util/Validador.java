package mx.ecommerce.util;

import java.util.List;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import mx.ecommerce.dao.AtributoDAO;
import mx.ecommerce.dao.CategoriaDAO;
import mx.ecommerce.dao.ProductoDAO;
import mx.ecommerce.dao.UsuarioDAO;
import mx.ecommerce.model.Atributo;
import mx.ecommerce.model.Categoria;
import mx.ecommerce.model.Producto;
import mx.ecommerce.model.Usuario;

import org.hibernate.HibernateException;

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

	public static boolean esNuloOVacio(Set<?> set) {
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

	public static boolean isDuplicado(Atributo model) throws Exception {
		List<Atributo> atributos = null;

		try {
			atributos = new AtributoDAO().findByName(model.getNombre());
			if (atributos != null && atributos.isEmpty()) {
				return false;
			} else
				for (Atributo atributo : atributos) {
					if (atributo.getId() != model.getId()) {
						return true;
					}
				}

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		return false;
	}
	
	public static boolean isDuplicado(Categoria model) throws Exception {
		List<Categoria> categorias = null;

		try {
			categorias = new CategoriaDAO().findByName(model.getNombre());
			if (categorias != null && categorias.isEmpty()) {
				return false;
			} else
				for (Categoria categoria : categorias) {
					if (categoria.getId() != model.getId()) {
						return true;
					}
				}

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		return false;
	}
	
	public static boolean isDuplicado(Usuario model) throws Exception {
		List<Usuario> usuarios = null;

		try {
			usuarios = new UsuarioDAO().findByCorreo(model.getCorreo());
			if (usuarios != null && !usuarios.isEmpty()) {
				return true;
			} else
				return false;

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}
	
	public static boolean isDuplicado(Producto model) throws Exception {
		List<Producto> productos = null;

		try {
			productos = new ProductoDAO().findByName(model.getNombre());
			if (productos != null && !productos.isEmpty()) {
				return true;
			} else
				return false;

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

}
