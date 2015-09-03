package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import mx.ecommerce.model.Atributo;

public class AtributoDAO extends GenericDAO {

	public void save(Atributo atributo) throws Exception {
		super.save(atributo);
	}
	
	public void update(Atributo atributo) throws Exception {
		super.update(atributo);
	}

	public void delete(Atributo atributo) throws Exception {
		super.delete(atributo);
	}

	public Atributo findById(Integer id) {
		return (Atributo) super.findById(Atributo.class, id);
	}
	
	public List<Atributo> findByName(String nombre) {
		List<Atributo> atributos = null;
		
		queryString = "from Atributo as atr where atr.nombre = :nombre";
		atributos = new ArrayList<Atributo>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("nombre", nombre);
			for (Object object : queryObject.list()) {
				atributos.add((Atributo) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return atributos;
	}
	
	public List<Atributo> findAll() {
		List<Atributo> atributos = new ArrayList<Atributo>();
		List<Object> objects = new ArrayList<Object>();

		objects = super.findAll(Atributo.class);
		
		for (Object object : objects) {
			atributos.add((Atributo) object);
		}	
		
		return atributos;
	}
}
