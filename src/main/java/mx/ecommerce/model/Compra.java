package mx.ecommerce.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Compra", catalog = "ECommerce")
public class Compra implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Usuario usuario;
	private Date fecha;
	private Estado estado;
	private Set<CompraProducto> productos = new HashSet<CompraProducto>(0);


	public Compra() {
	}

	public Compra(Usuario usuario, Date fecha, Estado estado) {
		this.usuario = usuario;
		this.fecha = fecha;
		this.estado = estado;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Usuariocorreo", referencedColumnName = "correo")	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false, length = 10)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Estadoid", referencedColumnName = "id")
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "compra")	
	public Set<CompraProducto> getProductos() {
		return productos;
	}

	public void setProductos(Set<CompraProducto> productos) {
		this.productos = productos;
	}

	
}
