package mx.ecommerce.bs;

import java.util.List;

import mx.ecommerce.dao.AtributoDAO;
import mx.ecommerce.model.Atributo;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class AtributoBs {
	public static List<Atributo> findAll() {
		return new AtributoDAO().findAll();
	}

	public static void save(Atributo model) throws Exception {
		try {
			validar(model);
			new AtributoDAO().save(model);

		
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void update(Atributo model) throws Exception {
		try {
			validar(model);
			new AtributoDAO().update(model);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void delete(Atributo model) throws Exception {
		try {
			new AtributoDAO().delete(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1451) {
					throw new ECommerceException("No es posible eliminar el atributo porque se encuentra asociado a otros elementos.", "MSG8");
				}
				System.out.println("ERROR CODE " + je.getErrorCode());
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static Atributo findById(Integer id) throws Exception {
		Atributo atributo = null;
		try {
			atributo = (Atributo) new AtributoDAO().findById(Atributo.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return atributo;
	}


	private static void validar(Atributo model) throws Exception {
		if (Validador.isDuplicado(model)) {
			throw new ECommerceValidacionException("El atributo "
					+ model.getNombre() + " ya existe.", "MSG7",
					new String[] { "El", "atributo", model.getNombre() },
					"model.nombre");
		}
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
	}
}
