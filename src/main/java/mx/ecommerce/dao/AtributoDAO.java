package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.model.Atributo;

public class AtributoDAO extends GenericDAO {

	public void save(Atributo atributo) throws Exception {
		super.save(atributo);
	}

	public Atributo findById(String id) {
		return (Atributo) super.findById(Atributo.class, id);
	}
	
	public List<Atributo> findAll() {
		List<Atributo> atributos = new ArrayList<>();
		List<Object> objects = new ArrayList<>();

		objects = super.findAll(Atributo.class);
		
		for (Object object : objects) {
			atributos.add((Atributo) object);
		}	
		
		return atributos;
	}
}
