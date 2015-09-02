package mx.ecomerce.dao;

import mx.ecomerce.model.Usuario;

import org.hibernate.HibernateException;


public class UsuarioDAO extends GenericDAO{

	public void save(Usuario usuario) throws Exception {
		try {
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	public Usuario findById(String id) {
		Usuario usuario = null;
		
		try {
			session.beginTransaction();
			usuario = (Usuario) session.get(Usuario.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}

		return usuario;

	}
}
