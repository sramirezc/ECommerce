package mx.ecommerce.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.ecommerce.bs.CategoriaBs;
import mx.ecommerce.bs.ProductoBs;
import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Atributo;
import mx.ecommerce.model.AtributoCategoria;
import mx.ecommerce.model.Categoria;
import mx.ecommerce.model.CategoriaProducto;
import mx.ecommerce.model.Producto;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.model.Valor;
import mx.ecommerce.util.ActionSupportECommerce;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.ErrorManager;
import mx.ecommerce.util.JsonUtil;
import mx.ecommerce.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/store/")
@Results({
		@Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
				"actionName", "store" }),
		@Result(name = "atributosCategoriaSel", type = "json", params = {
				"root", "atributosCategoriaSel" }) })
public class StoreCtrl extends ActionSupportECommerce implements SessionAware,
		ModelDriven<Producto> {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;
	Integer idSel;
	String nbCategoriaSel;
	private List<Atributo> atributosCategoriaSel;
	private String jsonValores;
	private String jsonCategorias;

	private Producto model;
	private List<Producto> listProductos;
	private List<Categoria> listCategorias;

	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		Collection<String> mensajes;

		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			resultado = INDEX;
			listProductos = ProductoBs.findAll();

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

	public String editNew() throws Exception {
		String resultado = null;
		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			listCategorias = CategoriaBs.findAll();
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
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			agregarAtributos();
			ProductoBs.save(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "producto",
					"registrado" }));

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
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}

			listCategorias = CategoriaBs.findAll();
			prepararVista();
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
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			
			agregarAtributos();
			ProductoBs.update(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "producto",
					"modificado" }));
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

	public String destroy() throws Exception {
		String resultado = null;
		try {
			if (!UsuarioBs.isAlmacen(usuario)) {
				resultado = Action.LOGIN;
			}
			ProductoBs.delete(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "producto",
					"eliminado" }));
			SessionManager.set(this.getActionMessages(), "mensajesAccion");
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String findAttributes() {
		try {
			atributosCategoriaSel = new ArrayList<Atributo>();
			for (AtributoCategoria categoriaAtributo : CategoriaBs.findByName(
					nbCategoriaSel).getAtributos()) {
				atributosCategoriaSel.add(categoriaAtributo.getAtributo());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "atributosCategoriaSel";
	}

	public void agregarAtributos() throws Exception {
		List<Categoria> categoriasVista = new ArrayList<Categoria>();
		List<Valor> valoresVista = new ArrayList<Valor>();
		Categoria categoriaAux = null;
		CategoriaProducto categoriaProducto = null;

		model.getCategorias().clear();

		valoresVista = JsonUtil.mapJSONToArrayList(jsonValores, Valor.class);
		categoriasVista = JsonUtil.mapJSONToArrayList(jsonCategorias,
				Categoria.class);

		for (Categoria categoriaVista : categoriasVista) {
			categoriaAux = CategoriaBs.findByName(categoriaVista.getNombre());
			categoriaProducto = new CategoriaProducto(categoriaAux, model);
			for (AtributoCategoria atributoCategoria : categoriaAux
					.getAtributos()) {
				categoriaProducto.getValores().add(
						new Valor(atributoCategoria, categoriaProducto,
								findValor(valoresVista,
										atributoCategoria.getAtributo())));
			}

			model.getCategorias().add(categoriaProducto);
		}
	}

	public String findValor(List<Valor> valores, Atributo atributo) {
		String valor = null;
		for (Valor valori : valores) {
			if (atributo.getNombre().equals(
					valori.getAtributoCategoria().getAtributo().getNombre())) {
				return valori.getValor();
			}
		}

		return valor;
	}

	public void prepararVista() {
		List<Valor> valores = new ArrayList<Valor>();
		List<Categoria> categorias = new ArrayList<Categoria>();
		Categoria categoriaAux = null;
		Valor valorAux = null;
		Atributo atributoAux = null;
		AtributoCategoria atributoCategoriaAux = null;

		
		for (CategoriaProducto categoriaProducto : model.getCategorias()) {
			categoriaAux = new Categoria();
			categoriaAux.setNombre(categoriaProducto.getCategoria().getNombre());
			categorias.add(categoriaAux);
			for (Valor valor : categoriaProducto.getValores()) {
				valorAux = new Valor();
				atributoAux = new Atributo();
				atributoCategoriaAux = new AtributoCategoria();
				atributoAux.setNombre(valor.getAtributoCategoria().getAtributo().getNombre());
				atributoCategoriaAux.setAtributo(atributoAux);
				valorAux.setValor(valor.getValor());
				valorAux.setAtributoCategoria(atributoCategoriaAux);
				valores.add(valorAux);
			}
		}
		
		this.jsonCategorias = JsonUtil.mapListToJSON(categorias);
		this.jsonValores = JsonUtil.mapListToJSON(valores);



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

	public Producto getModel() {
		return (model == null) ? model = new Producto() : model;
	}

	public int getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) throws Exception {
		this.idSel = idSel;
		model = ProductoBs.findById(idSel);

	}

	public String getJsonValores() {
		return jsonValores;
	}

	public void setJsonValores(String jsonValores) {
		this.jsonValores = jsonValores;
	}

	public List<Producto> getListProductos() {
		return listProductos;
	}

	public void setListProductos(List<Producto> listProductos) {
		this.listProductos = listProductos;
	}

	public String getNbCategoriaSel() {
		return nbCategoriaSel;
	}

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public void setNbCategoriaSel(String nombreCategoriaSel) {
		this.nbCategoriaSel = nombreCategoriaSel;
	}

	public List<Atributo> getAtributosCategoriaSel() {
		return atributosCategoriaSel;
	}

	public void setAtributosCategoriaSel(List<Atributo> atributosCategoriaSel) {
		this.atributosCategoriaSel = atributosCategoriaSel;
	}

	public String getJsonCategorias() {
		return jsonCategorias;
	}

	public void setJsonCategorias(String jsonCategorias) {
		this.jsonCategorias = jsonCategorias;
	}

}
