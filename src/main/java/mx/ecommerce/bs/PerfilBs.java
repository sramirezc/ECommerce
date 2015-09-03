package mx.ecommerce.bs;


public class PerfilBs {
	
	public enum Perfil {
		ADMINISTRADOR, ALMACEN, CLIENTE
	}
	public static int getId(Perfil perfil) {
		switch(perfil) {
		case ADMINISTRADOR:
			return 1;
		case ALMACEN:
			return 2;
		case CLIENTE:
			return 3;
		default:
			return 0;

		}
	}

}
