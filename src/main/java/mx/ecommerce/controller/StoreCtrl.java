package mx.ecommerce.controller;

import java.util.Collection;
import java.util.Map;

import mx.ecommerce.bs.UsuarioBs;
import mx.ecommerce.model.Usuario;
import mx.ecommerce.util.ActionSupportECommerce;
import mx.ecommerce.util.ECommerceException;
import mx.ecommerce.util.ErrorManager;
import mx.ecommerce.util.SessionManager;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;

@ResultPath("/content/store/")
@Results({
		@Result(name = ActionSupportECommerce.SUCCESS, type = "redirectAction", params = {
				"actionName", "store" })
		})
public class StoreCtrl extends ActionSupportECommerce implements SessionAware {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Usuario usuario;
	String resultado;

	@SuppressWarnings("unchecked")
	public String index() {
		Collection<String> mensajes;
		
		resultado = INDEX;
			
		try {
				usuario = SessionManager.consultarUsuarioActivo();
				if (!UsuarioBs.isAlmacen(usuario)) {
					resultado = Action.LOGIN;
					System.out.println("x");

				} else {
					System.out.println("y");
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

	

	public void setSession(Map<String, Object> session) {
		this.userSession = session;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

}
