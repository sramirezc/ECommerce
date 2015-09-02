package mx.ecommerce.dao;

import mx.ecommerce.model.Usuario;


public class UsuarioDAO extends GenericDAO{

	public void save(Usuario usuario) throws Exception {
		super.save(usuario);
	}

	public Usuario findById(String id) {
		return (Usuario) super.findById(Usuario.class, id);

	}
}
