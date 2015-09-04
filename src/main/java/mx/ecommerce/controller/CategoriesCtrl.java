package mx.ecommerce.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.ecommerce.bs.AtributoBs;
import mx.ecommerce.bs.CategoriaBs;
import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Atributo;
import mx.ecommerce.model.AtributoCategoria;
import mx.ecommerce.model.Categoria;
import mx.ecommerce.model.Usuario;
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

@ResultPath("/content/admin/categories/")
@Results({ @Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
		"actionName", "categories" }) })
public class CategoriesCtrl extends ActionSupportECommerce implements
		SessionAware, ModelDriven<Categoria> {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;
	Integer idSel;

	private Categoria model;
	private List<Categoria> listCategorias;
	private List<Atributo> listAtributos;
	private String jsonAtributosTabla;


	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		Collection<String> mensajes;

		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			resultado = INDEX;
			listCategorias = CategoriaBs.findAll();

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
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			listAtributos = AtributoBs.findAll();
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
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			agregarAtributos();
			CategoriaBs.save(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "La", "categoria",
					"registrada" }));

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
		List<Atributo> atributosSeleccionados = new ArrayList<Atributo>();
		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			
			listAtributos = AtributoBs.findAll();
			for (AtributoCategoria atributoCategoria : model.getAtributos()) {
				atributosSeleccionados.add(atributoCategoria.getAtributo());
			}
			jsonAtributosTabla = JsonUtil.mapListToJSON(atributosSeleccionados);
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
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			agregarAtributos();
			CategoriaBs.update(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "La", "categoria",
					"modificada" }));
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
			CategoriaBs.delete(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "La", "categoria",
					"eliminada" }));
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
	
	private void agregarAtributos() throws Exception {
		Set<Atributo> atributos = new HashSet<Atributo>(0);
		
		model.getAtributos().clear();
		if (jsonAtributosTabla != null && !jsonAtributosTabla.equals("")) {
			atributos = JsonUtil.mapJSONToSet(jsonAtributosTabla,
					Atributo.class);
		}
		
		for (Atributo atributo : atributos) {		
			model.getAtributos().add(new AtributoCategoria(AtributoBs.findByName(atributo.getNombre()), model));
		}
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

	public Categoria getModel() {
		return (model == null) ? model = new Categoria() : model;

	}

	public int getIdSel() {
		return idSel;
	}
	
	public void setIdSel(Integer idSel) throws Exception {
		this.idSel = idSel;
		model = CategoriaBs.findById(idSel);

	}

	public void setIdSel(int idSel) throws Exception {
		this.idSel = idSel;
		model = CategoriaBs.findById(idSel);
	}

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}
	

	public List<Atributo> getListAtributos() {
		return listAtributos;
	}

	public void setListAtributos(List<Atributo> listAtributos) {
		this.listAtributos = listAtributos;
	}

	public void setModel(Categoria model) {
		this.model = model;
	}

	public String getJsonAtributosTabla() {
		return jsonAtributosTabla;
	}

	public void setJsonAtributosTabla(String jsonAtributosTabla) {
		this.jsonAtributosTabla = jsonAtributosTabla;
	}
	
	

}
