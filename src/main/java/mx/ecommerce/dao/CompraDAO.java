package mx.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import mx.ecommerce.bs.CompraBs;
import mx.ecommerce.bs.CompraBs.EstadoEnum;
import mx.ecommerce.model.Compra;
import mx.ecommerce.model.CompraProducto;
import mx.ecommerce.model.Usuario;

import org.hibernate.HibernateException;

public class CompraDAO extends GenericDAO {

	public void save(Compra Compra) throws Exception {
		super.save(Compra);
	}
	
	public void update(Compra Compra) throws Exception {
		super.update(Compra);
	}

	public void delete(Compra Compra) throws Exception {
		super.delete(Compra);
	}

	public Compra findById(Integer id) {
		return (Compra) super.findById(Compra.class, id);
	}
	
	public List<Compra> findByState(EstadoEnum estado) {
		List<Compra> Compras = null;
		
		queryString = "from Compra as com where com.estado.id = :id";
		Compras = new ArrayList<Compra>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("id", CompraBs.getId(estado));
			for (Object object : queryObject.list()) {
				Compras.add((Compra) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return Compras;
	}
	
	public List<Compra> findAll() {
		List<Compra> Compras = new ArrayList<Compra>();
		List<Object> objects = new ArrayList<Object>();

		objects = super.findAll(Compra.class);
		
		for (Object object : objects) {
			Compras.add((Compra) object);
		}	
		return Compras;
	}

	public List<CompraProducto> findByProducto(CompraProducto compraProducto) {
		List<CompraProducto> comprasProducto = null;
		
		queryString = "from CompraProducto as comPro where comPro.producto.id = :idProducto and comPro.compra.id = :idCompra";
		comprasProducto = new ArrayList<CompraProducto>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("idProducto", compraProducto.getProducto().getId());
			queryObject.setParameter("idCompra", compraProducto.getCompra().getId());

			for (Object object : queryObject.list()) {
				comprasProducto.add((CompraProducto) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return comprasProducto;		
	}

	public List<Compra> findByUserState(Usuario usuario, EstadoEnum estado) {
		List<Compra> compras = null;
		
		queryString = "from Compra as com where com.usuario.id = :idUser and com.estado.id = :idEstado";
		compras = new ArrayList<Compra>();
		
		try {
			session.beginTransaction();
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("idUser", usuario.getId());
			queryObject.setParameter("idEstado", CompraBs.getId(estado));

			for (Object object : queryObject.list()) {
				compras.add((Compra) object);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		return compras;	
	}
}
