package mx.ecommerce.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Categoria_Producto", catalog = "ECommerce")
public class CategoriaProducto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Categoria categoria;
	private Producto producto;
	private Valor valor;

	public CategoriaProducto() {
	}

	public CategoriaProducto(Categoria categoria, Producto producto) {
		this.categoria = categoria;
		this.producto = producto;
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
    @JoinColumn(name = "Categoriaid", referencedColumnName = "id")
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Productoid", referencedColumnName = "id")
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "categoriaProducto", cascade = CascadeType.ALL, orphanRemoval = true)	
	public Valor getValor() {
		return valor;
	}

	public void setValor(Valor valor) {
		this.valor = valor;
	}
	
	

}
