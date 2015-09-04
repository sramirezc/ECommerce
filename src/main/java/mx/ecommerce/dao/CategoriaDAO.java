package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import mx.ecommerce.model.Categoria;

public class CategoriaDAO extends GenericDAO {

	public void save(Categoria categoria) throws Exception {
		super.save(categoria);
	}
	
	public void update(Categoria categoria) throws Exception {
		super.update(categoria);
	}

	public void delete(Categoria categoria) throws Exception {
		super.delete(categoria);
	}

	public Categoria findById(Integer id) {
		return (Categoria) super.findById(Categoria.class, id);
	}
	
	public List<Categoria> findByName(String nombre) {
		List<Categoria> categorias = null;
		
		queryString = "from Categoria as ctg where ctg.nombre = :nombre";
		categorias = new ArrayList<Categoria>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("nombre", nombre);
			for (Object object : queryObject.list()) {
				categorias.add((Categoria) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return categorias;
	}
	
	public List<Categoria> findAll() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		List<Object> objects = new ArrayList<Object>();

		objects = super.findAll(Categoria.class);
		
		for (Object object : objects) {
			categorias.add((Categoria) object);
		}	
		return categorias;
	}
}
