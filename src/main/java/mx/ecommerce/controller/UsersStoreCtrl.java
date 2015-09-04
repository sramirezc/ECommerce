package mx.ecommerce.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.ecommerce.bs.PerfilBs;
import mx.ecommerce.bs.PerfilBs.PerfilEnum;
import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.model.Perfil;
import mx.ecommerce.util.ActionSupportECommerce;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ECommerceValidacionException;
import mx.ecommerce.util.ErrorManager;
import mx.ecommerce.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

@ResultPath("/content/admin/users-store/")
@Results({ @Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
		"actionName", "users-store" }) })
public class UsersStoreCtrl extends ActionSupportECommerce implements
		SessionAware, ModelDriven<Usuario> {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;
	Integer idSel;

	private Usuario model;
	private List<Usuario> listUsuariosAlmacen;

	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		Collection<String> mensajes;

		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			resultado = INDEX;
			listUsuariosAlmacen = UsuarioBs.findByPerfil(PerfilBs.PerfilEnum.ALMACEN);

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
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
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
			Perfil perfil =  PerfilBs.findById(PerfilBs.getId(PerfilEnum.ALMACEN));
			model.setPerfil(perfil);
			UsuarioBs.save(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "atributo",
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
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
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
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			UsuarioBs.update(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "usuario",
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
			UsuarioBs.delete(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El", "usuario",
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
		model = UsuarioBs.findById(idSel);
	}

}
