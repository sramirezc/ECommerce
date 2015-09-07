package mx.ecommerce.bs;

import mx.ecommerce.dao.EstadoDAO;
import mx.ecommerce.model.Estado;

import org.hibernate.HibernateException;

public class EstadoBs {
	public static Estado findById(Integer id) throws Exception {
		Estado estado = null;
		try {
			estado = (Estado) new EstadoDAO().findById(Estado.class, id);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
		return estado;
	}
}
