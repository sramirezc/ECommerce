package mx.ecommerce.util;

public class ECommerceValidacionException extends ECommerceException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String campo;
	public ECommerceValidacionException(String message, String idMensaje,
			String[] parametros) {
		super(message, idMensaje, parametros);
	}
	public ECommerceValidacionException(String message, String idMensaje,
			String[] parametros, String campo) {
		super(message, idMensaje, parametros);
		this.campo = campo;
	}
	public ECommerceValidacionException(String message, String idMensaje) {
		super(message, idMensaje);
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	
}
