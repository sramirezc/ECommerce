package mx.ecomerce.util;


public class ECommerceException extends RuntimeException{

	/**
	 * 
	 */
	private String idMensaje;
	private String[] parametros; 
	private static final long serialVersionUID = 1L;
	
		
		public ECommerceException(String message, String idMensaje) {
		super(message);
		this.idMensaje = idMensaje;
	}
		public ECommerceException (String message, String idMensaje, String[] parametros) {
	        super (message);
	        this.idMensaje = idMensaje;
	        this.parametros = parametros;
	    }

		public ECommerceException (String message) {
	        super (message);
	    }
	
	    public ECommerceException (Throwable cause) {
	        super (cause);
	    }
	
	    public ECommerceException (String message, Throwable cause) {
	        super (message, cause);
	    }

		public String getIdMensaje() {
			return idMensaje;
		}

		public void setIdMensaje(String idMensaje) {
			this.idMensaje = idMensaje;
		}
		public String[] getParametros() {
			return parametros;
		}
		public void setParametros(String[] parametros) {
			this.parametros = parametros;
		}

		
	    
	    

}
