package mx.ecommerce.dao;

import java.util.List;

import mx.ecommerce.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GenericDAO {
	protected static Session session = null;
	protected Query queryObject;
	protected String queryString;

	public GenericDAO() {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public void save(Object object) throws Exception {
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public void update(Object object) throws Exception {
		try {
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public void delete(Object object)  throws Exception {
		try {
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
	}
	
	public Object findById(Class<?> clazz, Integer id) {
		Object object = null;
		
		try {
			session.beginTransaction();
			object = (Object) session.get(clazz, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return object;

	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findAll(Class<?> clazz) {
		List<Object> objects = null;
		queryString = "from " + clazz.getName();

		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			objects = queryObject.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
			throw he;
		}
		return objects;

	}
	
	}

