package mx.ecommerce.dao;

import mx.ecommerce.model.Perfil;


public class PerfilDAO extends GenericDAO {
	public Perfil findById(Integer id) {
		return (Perfil) super.findById(Perfil.class, id);
	}
}
