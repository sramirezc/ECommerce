package mx.ecommerce.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Atributo_Categoria", catalog = "ECommerce")
public class AtributoCategoria implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Atributo atributo;
	private Categoria categoria;

	public AtributoCategoria() {
	}

	public AtributoCategoria(Atributo atributo, Categoria categoria) {
		this.atributo = atributo;
		this.categoria = categoria;
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
    @JoinColumn(name = "Atributoid", referencedColumnName = "id")	
	public Atributo getAtributo() {
		return this.atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Categoriaid", referencedColumnName = "id")
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
