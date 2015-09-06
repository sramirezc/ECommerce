package mx.ecommerce.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.ecommerce.bs.CategoriaBs;
import mx.ecommerce.bs.PerfilBs;
import mx.ecommerce.bs.ProductoBs;
import mx.ecommerce.bs.PerfilBs.PerfilEnum;
import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Categoria;
import mx.ecommerce.model.CategoriaProducto;
import mx.ecommerce.model.Producto;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.model.Perfil;
import mx.ecommerce.util.ActionSupportECommerce;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.ErrorManager;
import mx.ecommerce.util.JsonUtil;
import mx.ecommerce.util.SessionManager;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRef(value = "defaultStack")
@ResultPath("/content/customer/")
@Results({
		@Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
				"actionName", "customer" }),
		@Result(name = "buy", type = "dispatcher", location = "buy.jsp") })
public class CustomerCtrl extends ActionSupportECommerce implements
		SessionAware, ModelDriven<Usuario> {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;
	Integer idSel;
	private Usuario model;
	private List<Usuario> listUsuariosAlmacen;

	private String jsonCategorias;
	private String jsonCategoriasSel;
	private String searchProducto;

	private List<Producto> listProductos;
	private List<Categoria> listCategorias;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		Collection<String> mensajes;
		try {
			if (SessionManager.isLogged()) {
				usuario = SessionManager.consultarUsuarioActivo();
				if (!UsuarioBs.isCliente(usuario)) {
					resultado = Action.LOGIN;
					return resultado;
				}
			} else {
				resultado = Action.LOGIN;
				return resultado;
			}
			resultado = INDEX;

			mensajes = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(mensajes);
			SessionManager.delete("mensajesAccion");

		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String editNew() throws Exception {
		String resultado = null;
		try {
			resultado = EDITNEW;
		} catch (ECommerceException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			resultado = index();
		}
		return resultado;
	}

	public String create() throws Exception {
		String resultado = null;
		try {
			Perfil perfil = PerfilBs.findById(PerfilBs
					.getId(PerfilEnum.CLIENTE));
			model.setPerfil(perfil);
			UsuarioBs.save(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "Su",
					"información", "registrada" }));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = editNew();
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String edit() throws Exception {
		String resultado = null;
		try {
			if (SessionManager.isLogged()) {
				usuario = SessionManager.consultarUsuarioActivo();
				if (!UsuarioBs.isCliente(usuario)) {
					resultado = Action.LOGIN;
					return resultado;
				}
				this.model = usuario;
				this.idSel = model.getId();
			} else {
				resultado = Action.LOGIN;
				return resultado;
			}
			resultado = EDIT;
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}

		return resultado;

	}

	public String update() throws Exception {
		String resultado = null;
		try {
			if (SessionManager.isLogged()) {
				usuario = SessionManager.consultarUsuarioActivo();
				if (!UsuarioBs.isCliente(usuario)) {
					resultado = Action.LOGIN;
					return resultado;
				}
			} else {
				resultado = Action.LOGIN;
				return resultado;
			}
			UsuarioBs.update(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "Su",
					"información", "modificada" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = edit();
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String buy() throws Exception {
		String resultado = null;
		try {
			if (SessionManager.isLogged()) {
				usuario = SessionManager.consultarUsuarioActivo();
				if (!UsuarioBs.isCliente(usuario)) {
					resultado = Action.LOGIN;
					return resultado;
				}
			} else {
				resultado = Action.LOGIN;
				return resultado;
			}
			resultado = "buy";
			listProductos = ProductoBs.findAll();
			listCategorias = CategoriaBs.findAll();
		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = buy();
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public String search() throws Exception {
		Collection<String> mensajes;
		Set<Producto> oldProductos = new HashSet<Producto>();
		Set<Producto> newProductos = new HashSet<Producto>();
		listProductos = new ArrayList<Producto>();
		boolean isFirst = true;

		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			resultado = "buy";
			listCategorias = CategoriaBs.findAll();
			listProductos.clear();
			searchByCategoria:
			for (Categoria categoria : JsonUtil.mapJSONToArrayList(
					jsonCategoriasSel, Categoria.class)) {
				newProductos.clear();
				for (CategoriaProducto categoriaProducto : CategoriaBs
						.findByName(categoria.getNombre()).getProductos()) {
					if (searchProducto != null && searchProducto != "") {
						if (categoriaProducto.getProducto().getNombre()
								.contains(searchProducto)) {
							newProductos.add(categoriaProducto.getProducto());
						}
					} else {
						newProductos.add(categoriaProducto.getProducto());
					}
				}

				if (isFirst) {
					oldProductos.addAll(newProductos);
					isFirst = false;
				} else {
					oldProductos = intersect(oldProductos, newProductos);
					if (oldProductos.isEmpty()) {
						break searchByCategoria;
					}
				}	
			}

			if (JsonUtil.mapJSONToArrayList(jsonCategoriasSel, Categoria.class)
					.isEmpty()) {
				listProductos = ProductoBs.findAll();
			} else {
				listProductos.addAll(oldProductos);
			}
			mensajes = (Collection<String>) SessionManager
					.get("mensajesAccion");
			this.setActionMessages(mensajes);
			SessionManager.delete("mensajesAccion");

		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = Action.LOGIN;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public Set<Producto> intersect(Set<Producto> oldSet, Set<Producto> newSet) {
		Set<Producto> productos = new HashSet<Producto>();
		for (Producto oldProducto : oldSet) {
			for (Producto newProducto : newSet) {
				if (oldProducto.getNombre().equals(newProducto.getNombre())) {
					productos.add(oldProducto);
				}
			}
		}

		return productos;
	}

	public void setSession(Map<String, Object> session) {
		this.userSession = session;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public List<Usuario> getListUsuariosAlmacen() {
		return listUsuariosAlmacen;
	}

	public void setListUsuariosAlmacen(List<Usuario> listUsuariosAlmacen) {
		this.listUsuariosAlmacen = listUsuariosAlmacen;
	}

	public void setModel(Usuario model) {
		this.model = model;
	}

	public Usuario getModel() {
		return (model == null) ? model = new Usuario() : model;

	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) throws Exception {
		this.idSel = idSel;
		if (idSel != null) {
			model = UsuarioBs.findById(idSel);
		}
	}

	
	public String getJsonCategorias() {
		return jsonCategorias;
	}


	public void setJsonCategorias(String jsonCategorias) {
		this.jsonCategorias = jsonCategorias;
	}


	public String getJsonCategoriasSel() {
		return jsonCategoriasSel;
	}


	public void setJsonCategoriasSel(String jsonCategoriasSel) {
		this.jsonCategoriasSel = jsonCategoriasSel;
	}


	public String getSearchProducto() {
		return searchProducto;
	}


	public void setSearchProducto(String searchProducto) {
		this.searchProducto = searchProducto;
	}


	public List<Producto> getListProductos() {
		return listProductos;
	}


	public void setListProductos(List<Producto> listProductos) {
		this.listProductos = listProductos;
	}

	
	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	
	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

}
