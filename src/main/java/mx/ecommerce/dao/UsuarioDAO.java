package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.bs.PerfilBs;
import mx.ecommerce.bs.PerfilBs.PerfilEnum;
import mx.ecommerce.model.Usuario;

import org.hibernate.HibernateException;


public class UsuarioDAO extends GenericDAO{

	public void save(Usuario usuario) throws Exception {
		super.save(usuario);
	}

	public Usuario findById(Integer id) {
		return (Usuario) super.findById(Usuario.class, id);
	}
	
	public List<Usuario> findByCorreo(String correo) {
		List<Usuario> usuarios = null;
		
		queryString = "from Usuario usr where usr.correo = :correo";
		usuarios = new ArrayList<Usuario>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("correo", correo);
			for (Object object : queryObject.list()) {
				usuarios.add((Usuario) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return usuarios;
	}
	
	public List<Usuario> findByPerfil(PerfilEnum perfil) {
		List<Usuario> usuarios = null;
		
		queryString = "from Usuario usr where usr.perfil.id = :idPerfil";
		usuarios = new ArrayList<Usuario>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("idPerfil", PerfilBs.getId(perfil));
			for (Object object : queryObject.list()) {
				usuarios.add((Usuario) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return usuarios;
	}
}
