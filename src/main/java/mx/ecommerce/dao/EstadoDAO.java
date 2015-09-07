package mx.ecommerce.dao;

import mx.ecommerce.model.Estado;

public class EstadoDAO extends GenericDAO {
	public Estado findById(Integer id) {
		return (Estado) super.findById(Estado.class, id);
	}
}
