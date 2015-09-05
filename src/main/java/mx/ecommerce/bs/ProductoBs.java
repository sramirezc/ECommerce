package mx.ecommerce.bs;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.dao.ProductoDAO;
import mx.ecommerce.model.CategoriaProducto;
import mx.ecommerce.model.Producto;
import mx.ecommerce.model.Valor;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class ProductoBs {
	
	public static List<Producto> findAll() {
		return new ProductoDAO().findAll();
	}

	public static void save(Producto model) throws Exception {
		try {
			validar(model);
			new ProductoDAO().save(model);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void update(Producto model) throws Exception {
		try {
			validar(model);
			new ProductoDAO().update(model);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void delete(Producto model) throws Exception {
		try {
			new ProductoDAO().delete(model);
		} catch (JDBCException je) {
				if(je.getErrorCode() == 1451) {
					throw new ECommerceException("No es posible eliminar el producto porque se encuentra asociado a otros elementos.", "MSG8");
				}
				je.printStackTrace();
				throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static Producto findById(Integer id) throws Exception {
		Producto producto = null;
		try {
			producto = (Producto) new ProductoDAO().findById(Producto.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return producto;
	}
	
	public static Producto findByName(String name) throws Exception {
		List<Producto> productos = new ArrayList<Producto>();
		try {
			productos = new ProductoDAO().findByName(name);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return productos.get(0);
	}


	private static void validar(Producto model) throws Exception {
		if (Validador.isDuplicado(model)) {
			throw new ECommerceValidacionException("El producto "
					+ model.getNombre() + " ya existe.", "MSG7",
					new String[] { "El", "producto", model.getNombre() },
					"model.nombre");
		}
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el nombre del producto.", "MSG2",
					null, "model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), 50)) {
			throw new ECommerceValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { "50", "caracteres" }, "model.nombre");
		}
		if (model.getCategorias().isEmpty()) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó ninguna categoria.", "MSG10",
					null, "formError");
		} else {
			for (CategoriaProducto categoriaProducto : model.getCategorias()) {
				for (Valor valor : categoriaProducto.getValores()) {
				if (Validador.esNuloOVacio(valor.getValor())) {
					throw new ECommerceValidacionException(
							"El usuario no ingresó el valor del atributo.", "MSG11",
							null, "atributos");
				}
				}
			}
		}
	}
}
