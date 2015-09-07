package mx.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Producto", catalog = "ECommerce", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Producto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private Integer cantidad;
	private Float precio;
	private Set<CategoriaProducto> categorias = new HashSet<CategoriaProducto>(0);

	public Producto() {
	}

	public Producto(String nombre, Integer cantidad, Float precio) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
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

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)	
	public Set<CategoriaProducto> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<CategoriaProducto> categorias) {
		this.categorias = categorias;
	}
	
	@Column(name = "cantidad", unique = false, nullable = false)
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "precio", unique = false, nullable = false)
	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	
	
}
