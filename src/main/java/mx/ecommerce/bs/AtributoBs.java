package mx.ecommerce.bs;

import java.util.List;

import mx.ecommerce.dao.AtributoDAO;
import mx.ecommerce.model.Atributo;

public class AtributoBs {
	public static List <Atributo> findAll() {
		return new AtributoDAO().findAll();
	}
}
