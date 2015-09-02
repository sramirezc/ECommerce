package mx.ecomerce.controller;

import java.util.Collection;
import java.util.Map;

import mx.ecomerce.bs.AccessBs;
import mx.ecomerce.bs.UsuarioBs;
import mx.ecomerce.model.Usuario;
import mx.ecomerce.util.ActionSupportECommerce;
import mx.ecomerce.util.ECommerceException;
import mx.ecomerce.util.ECommerceValidacionException;
import mx.ecomerce.util.ErrorManager;
import mx.ecomerce.util.SessionManager;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

@InterceptorRef(value="defaultStack")
@Results({
		@Result(name = "admin", type = "redirectAction", params = {
				"actionName", "admin" }),
		@Result(name = "store", type = "redirectAction", params = {
				"actionName", "store" }),
		@Result(name = "customer", type = "redirectAction", params = {
				"actionName", "customer" }),
		@Result(name = "recover", type = "dispatcher", location = "recover.jsp")
		})
public class AccessCtrl extends ActionSupportECommerce implements SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;
	private String userName;
	private String password;

	@SuppressWarnings("unchecked")
	public String index() {
		Collection<String> mensajes;
		
		resultado = INDEX;
			
		try {
			if (SessionManager.isLogged()) {
				usuario = SessionManager.consultarUsuarioActivo();
				if (UsuarioBs.isAdministrador(usuario)) {
					resultado = "admin";
				} else if (UsuarioBs.isCliente(usuario)) {
					resultado = "customer";
				} else {
					resultado = "store";
				}
			}
			mensajes = (Collection<String>) SessionManager.get("mensajesAccion");	
			this.setActionMessages(mensajes);
			SessionManager.delete("mensajesAccion");

		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String login() throws Exception {
		Map<String, Object> session = null;
		try {
			if (userSession != null) {
				userSession.clear();
			}
			usuario = AccessBs.verificarLogin(userName, password);
			session = ActionContext.getContext().getSession();
			session.put("login", true);
			session.put("correo", usuario.getCorreo());
			setSession(session);
			if (UsuarioBs.isAdministrador(usuario)) {
				resultado = "admin";
			} else if (UsuarioBs.isCliente(usuario)) {
				resultado = "customer";
			} else {
				resultado = "store";
			}

		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			return index();
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public String logout() {
		if (userSession != null) {
			userSession.clear();
		}
		return index();
	}

	public String recover() {
		String resultado = null;
		try {
			resultado = "recover";
		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = recover();
		} catch (ECommerceException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String sendPassword() {
		String resultado = null;
		try {
			AccessBs.recuperarContrasenia(userName);
			resultado = INDEX;
			addActionMessage(getText("MSG32"));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");

		} catch (ECommerceValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = recover();
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
