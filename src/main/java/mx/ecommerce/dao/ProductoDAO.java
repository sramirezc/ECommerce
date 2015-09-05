package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.model.Producto;

import org.hibernate.HibernateException;


public class ProductoDAO extends GenericDAO {

	public void save(Producto producto) throws Exception {
		super.save(producto);
	}
	
	public void update(Producto producto) throws Exception {
		super.update(producto);
	}

	public void delete(Producto producto) throws Exception {
		super.delete(producto);
	}

	public Producto findById(Integer id) {
		return (Producto) super.findById(Producto.class, id);
	}
	
	public List<Producto> findByName(String nombre) {
		List<Producto> productos = null;
		
		queryString = "from Producto as pro where pro.nombre = :nombre";
		productos = new ArrayList<Producto>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("nombre", nombre);
			for (Object object : queryObject.list()) {
				productos.add((Producto) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return productos;
	}
	
	public List<Producto> findAll() {
		List<Producto> productos = new ArrayList<Producto>();
		List<Object> objects = new ArrayList<Object>();

		objects = super.findAll(Producto.class);
		
		for (Object object : objects) {
			productos.add((Producto) object);
		}	
		
		return productos;
	}
}
