package mx.ecommerce.bs;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.bs.PerfilBs.PerfilEnum;
import mx.ecommerce.dao.UsuarioDAO;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class UsuarioBs {
	
	public static void save(Usuario model) throws Exception {
		try {
			if (Validador.isDuplicado(model)) {
				throw new ECommerceValidacionException("El usuario "
						+ model.getCorreo() + " ya existe.", "MSG7",
						new String[] { "El", "usuario", model.getCorreo() },
						"model.nombre");
			}
			validar(model);
			new UsuarioDAO().save(model);

		
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void update(Usuario model) throws Exception {
		try {
			validar(model);
			new UsuarioDAO().update(model);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void delete(Usuario model) throws Exception {
		try {
			new UsuarioDAO().delete(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1451) {
					throw new ECommerceException("No es posible eliminar el usuario porque se encuentra asociado a otros elementos.", "MSG8");
				}
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public static Usuario findById(Integer id) throws Exception {
		Usuario usuario = null;
		try {
			usuario = (Usuario) new UsuarioDAO().findById(Usuario.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return usuario;
	}
	
	public static List<Usuario> findByPerfil(PerfilEnum perfil) throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			usuarios = new UsuarioDAO().findByPerfil(perfil);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		return usuarios;
	}
	
	public static boolean isAdministrador(Usuario model) {
		if (model.getPerfil().getId() == PerfilBs.getId(PerfilEnum.ADMINISTRADOR)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isCliente(Usuario model) {
		if (model.getPerfil().getId() == PerfilBs.getId(PerfilEnum.CLIENTE)) {
			return true;
		} else {
			return false;
		}	}
	
	public static boolean isAlmacen(Usuario model) {
		if (model.getPerfil().getId() == PerfilBs.getId(PerfilEnum.ALMACEN)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void validar(Usuario model) throws Exception {

		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el nombre del atributo.", "MSG2",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.nombre");
		}		
		if (Validador.esNuloOVacio(model.getApPaterno())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el apellido paterno.", "MSG2",
					null, "model.apPaterno");
		}
		if (Validador.validaLongitudMaxima(model.getApPaterno(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un apellido paterno muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.apPaterno");
		}
		if (Validador.esNuloOVacio(model.getApMaterno())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el apellido materno.", "MSG2",
					null, "model.apMaterno");
		}
		if (Validador.validaLongitudMaxima(model.getApMaterno(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un apellido materno muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.apPaterno");
		}
		if (Validador.esNuloOVacio(model.getCorreo())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el correo.", "MSG2",
					null, "model.correo");
		}
		if (!Validador.esCorreo(model.getCorreo())) {
			throw new ECommerceValidacionException(
					"El usuario ingresó un correo inválido.", "MSG9",
					null, "model.correo");
		}
		if (Validador.validaLongitudMaxima(model.getCorreo(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un correo muy largo.", "MSG6",
					new String[] { "20", "caracteres" }, "model.correo");
		}
		if (Validador.esNuloOVacio(model.getPassword())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el password.", "MSG2",
					null, "model.password");
		}
		if (Validador.validaLongitudMaxima(model.getPassword(), 20)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un password muy largo.", "MSG6",
					new String[] { "20", "caracteres" }, "model.password");
		}
	}

	
}
