package mx.ecommerce.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.ecommerce.bs.AtributoBs;
import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Atributo;
import mx.ecommerce.model.Usuario;
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

@ResultPath("/content/admin/attributes/")
@Results({ @Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
		"actionName", "attributes" })
})
public class AttributesCtrl extends ActionSupportECommerce implements SessionAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;

	private Atributo model;
	private List<Atributo> listAtributos;
	
	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		Collection<String> mensajes;

		try {
			usuario = SessionManager.consultarUsuarioActivo();
			if (!UsuarioBs.isAdministrador(usuario)) {
				resultado = Action.LOGIN;
			}
			resultado = INDEX;
			listAtributos = AtributoBs.findAll();
			
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
			resultado = EDITNEW;
		} catch (ECommerceException pe) {
			System.err.println(pe.getMessage());
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.agregaMensajeError(this, e);
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
			AtributoBs.save(model);
			resultado = SUCCESS;
			addActionMessage(getText("MSG5", new String[] { "El",
					"Atributo", "registrado" }));

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

	public void setSession(Map<String, Object> session) {
		this.userSession = session;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public List<Atributo> getListAtributos() {
		return listAtributos;
	}

	public void setListAtributos(List<Atributo> listAtributos) {
		this.listAtributos = listAtributos;
	}


	

}
