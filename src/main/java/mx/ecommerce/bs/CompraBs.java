package mx.ecommerce.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.ecommerce.dao.CompraDAO;
import mx.ecommerce.dao.ProductoDAO;
import mx.ecommerce.model.Compra;
import mx.ecommerce.model.CompraProducto;
import mx.ecommerce.model.Producto;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.SessionManager;
import mx.ecommerce.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class CompraBs {

	public enum EstadoEnum {
		FINALIZADA, PENDIENTE
	}

	public static int getId(EstadoEnum estado) {
		switch (estado) {
		case FINALIZADA:
			return 1;
		case PENDIENTE:
			return 2;
		default:
			return 0;

		}
	}

	public static List<Compra> findAll() {
		return new CompraDAO().findAll();
	}

	public static void save(Compra compra) throws Exception {
		try {
			new CompraDAO().save(compra);

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void update(Compra compra) throws Exception {
		try {
			new CompraDAO().update(compra);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}

	public static void delete(Compra compra) throws Exception {
		try {
			new CompraDAO().delete(compra);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1451) {
				throw new ECommerceException(
						"No es posible eliminar la compra porque se encuentra asociado a otros elementos.",
						"MSG8");
			}
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

	}

	public static Compra findById(Integer id) throws Exception {
		Compra compra = null;
		try {
			compra = (Compra) new CompraDAO().findById(Compra.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

		return compra;
	}

	public static Compra findByState(EstadoEnum estado) throws Exception {
		List<Compra> compras = new ArrayList<Compra>();
		try {
			compras = new CompraDAO().findByState(estado);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		if (compras == null || compras.isEmpty()) {
			return null;
		} else {
			return compras.get(0);
		}
	}
	
	public static List<Compra> findByUserState(Usuario usuario, EstadoEnum estado) throws Exception {
		List<Compra> compras = new ArrayList<Compra>();
		try {
			compras = new CompraDAO().findByUserState(usuario, estado);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		if (compras == null) {
			return null;
		} else {
			return compras;
		}
	}

	
private static CompraProducto findByProducto(Producto producto,
			Compra compra) throws Exception {
		List<CompraProducto> comprasProducto = new ArrayList<CompraProducto>();
		CompraProducto compraProducto = new CompraProducto();
		compraProducto.setCompra(compra);
		compraProducto.setProducto(producto);
		try {
			comprasProducto = new CompraDAO().findByProducto(compraProducto);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		if (comprasProducto == null || comprasProducto.isEmpty()) {
			return null;
		} else {

		}
		return comprasProducto.get(0);
	}
	
	public static void addProduct(String nbProductoSel, String cantProductoSel)
			throws Exception {
		Compra compra;
		CompraProducto compraProducto;
		Producto producto;
		if (Validador.esNuloOVacio(nbProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el nombre del producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		if (Validador.esNuloOVacio(cantProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó la cantidad de producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		if (!Validador.isInteger(cantProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó la cantidad de producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		compra = findByState(EstadoEnum.PENDIENTE);
		producto = ProductoBs.findByName(nbProductoSel);
		if (producto.getCantidad() >= Integer.parseInt(cantProductoSel)) {
			if (compra == null) {
				compra = new Compra(SessionManager.consultarUsuarioActivo(),
						new Date(),
						EstadoBs.findById(getId(EstadoEnum.PENDIENTE)));
				compraProducto = new CompraProducto(compra, producto,
						Integer.parseInt(cantProductoSel));
				compra.getProductos().add(compraProducto);
				producto.setCantidad(producto.getCantidad()
						- Integer.parseInt(cantProductoSel));
				new CompraDAO().save(compra);
				new ProductoDAO().update(producto);
			} else {
				compraProducto = findByProducto(producto, compra);
				if (compraProducto == null) {
					compraProducto = new CompraProducto(compra, producto,
							Integer.parseInt(cantProductoSel));
					compra.getProductos().add(compraProducto);
					producto.setCantidad(producto.getCantidad()
							- Integer.parseInt(cantProductoSel));
					new CompraDAO().update(compra);
					new ProductoDAO().update(producto);
				} else {
					compraProducto.setCantidad(compraProducto.getCantidad()
							+ Integer.parseInt(cantProductoSel));
					producto.setCantidad(producto.getCantidad()
							- Integer.parseInt(cantProductoSel));

					new CompraDAO().update(compraProducto);
					new ProductoDAO().update(producto);
				}
			}
		} else {
			throw new ECommerceValidacionException(
					"No hay suficientes productos en el almacén.", "MSG14",
					null, "errorBuy");
		}
	}
	
	public static void editProduct(String nbProductoSel, String cantProductoSel)
			throws Exception {
		Compra compra;
		CompraProducto compraProducto;
		Producto producto;
		Integer diferencia;
		if (Validador.esNuloOVacio(nbProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó el nombre del producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		if (Validador.esNuloOVacio(cantProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó la cantidad de producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		if (!Validador.isInteger(cantProductoSel)) {
			throw new ECommerceValidacionException(
					"El usuario no ingresó la cantidad de producto.", "MSG13",
					null, "errorConfirmarProducto");
		}
		compra = findByState(EstadoEnum.PENDIENTE);
		producto = ProductoBs.findByName(nbProductoSel);
		compraProducto = findByProducto(producto, compra);
		if (compraProducto.getCantidad() >= Integer.parseInt(cantProductoSel)) {
			diferencia = compraProducto.getCantidad() - Integer.parseInt(cantProductoSel);
			producto.setCantidad(producto.getCantidad() + diferencia);
			compraProducto.setCantidad(Integer.parseInt(cantProductoSel));
			new CompraDAO().update(compraProducto);
			new ProductoDAO().update(producto);
		} else {
			diferencia = Integer.parseInt(cantProductoSel) - compraProducto.getCantidad();
			if (diferencia <= producto.getCantidad()) {
				System.out.println("test2");
				producto.setCantidad(producto.getCantidad() - diferencia);
				compraProducto.setCantidad(Integer.parseInt(cantProductoSel));
				new CompraDAO().update(compraProducto);
				new ProductoDAO().update(producto);

			} else {
				throw new ECommerceValidacionException(
						"No hay suficientes productos en el almacén.", "MSG14",
						null, "errorBuy");
			}
			

		}
		
		
		
	}

	public static void deleteProduct(String nbProductoSel) throws Exception {
		Compra compra = findByState(EstadoEnum.PENDIENTE);
		Producto producto = ProductoBs.findByName(nbProductoSel);
		CompraProducto compraProducto = findByProducto(producto, compra);
		producto.setCantidad(producto.getCantidad() + compraProducto.getCantidad());
		new CompraDAO().delete(compraProducto);
		new ProductoDAO().update(producto);
	}
	
	public static void finishBuy() throws Exception {
		Compra compra = null;
		try {
			compra = findByState(EstadoEnum.PENDIENTE);
			if (compra == null || compra.getProductos().isEmpty()) {
				throw new ECommerceValidacionException(
						"No hay nada por comprar", "MSG14",
						null, "errorConfirmarProducto");
			}
			compra.setEstado(EstadoBs.findById(getId(EstadoEnum.FINALIZADA)));
			new CompraDAO().update(compra);

		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
	}
}