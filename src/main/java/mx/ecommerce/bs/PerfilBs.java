package mx.ecommerce.bs;

import mx.ecommerce.dao.PerfilDAO;
import mx.ecommerce.model.Perfil;

import org.hibernate.HibernateException;


public class PerfilBs {
	
	public enum PerfilEnum {
		ADMINISTRADOR, ALMACEN, CLIENTE
	}
	public static int getId(PerfilEnum perfil) {
		switch(perfil) {
		case ADMINISTRADOR:
			return 1;
		case ALMACEN:
			return 2;
		case CLIENTE:
			return 3;
		default:
			return 0;

		}
	}

	public static Perfil findById(Integer id) throws Exception {
		Perfil perfil = null;
		try {
			perfil = (Perfil) new PerfilDAO().findById(Perfil.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return perfil;
	}
	
}
