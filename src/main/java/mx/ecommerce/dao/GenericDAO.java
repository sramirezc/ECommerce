package mx.ecommerce.dao;

import java.util.List;

import mx.ecommerce.model.Usuario;
import mx.ecommerce.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class GenericDAO {
	protected static Session session = null;

	public GenericDAO() {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public Object findById(Class<?> clazz, String id) {
		Object object = null;
		
		try {
			session.beginTransaction();
			object = (Usuario) session.get(clazz, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return object;

	}
	
	public void save(Object object) throws Exception {
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findAll(Class<?> clazz) {
		List<Object> objects = null;
		
		try {
			session.beginTransaction();
			objects = session.createCriteria(clazz).list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return objects;

	}
	
	}

