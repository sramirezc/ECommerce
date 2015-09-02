package mx.ecomerce.dao;

import mx.ecomerce.util.HibernateUtil;

import org.hibernate.Session;

public class GenericDAO {
	protected static Session session = null;

	public GenericDAO() {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
	}

