package mx.ecommerce.bs;

import java.util.List;

import mx.ecommerce.dao.CategoriaDAO;
import mx.ecommerce.model.Categoria;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class CategoriaBs {
	
	public static List<Categoria> findAll() {
		return new CategoriaDAO().findAll();
	}

	public static void save(Categoria model) throws Exception {
		try {
			validar(model);
			new CategoriaDAO().save(model);

		
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void update(Categoria model) throws Exception {
		try {
			validar(model);
			new CategoriaDAO().update(model);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void delete(Categoria model) throws Exception {
		try {
			new CategoriaDAO().delete(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1451) {
					throw new ECommerceException("No es posible eliminar el Categoria porque se encuentra asociado a otros elementos.", "MSG8");
				}
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static Categoria findById(Integer id) throws Exception {
		Categoria Categoria = null;
		try {
			Categoria = (Categoria) new CategoriaDAO().findById(Categoria.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return Categoria;
	}


	private static void validar(Categoria model) throws Exception {
		if (Validador.isDuplicado(model)) {
			throw new ECommerceValidacionException("La categoria "
					+ model.getNombre() + " ya existe.", "MSG7",
					new String[] { "La", "categoria", model.getNombre() },
					"model.nombre");
		}
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el nombre del categoria.", "MSG2",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.nombre");
		}
	}
}
