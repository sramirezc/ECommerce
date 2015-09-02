package mx.ecommerce.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Categoria", catalog = "ECommerce", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Categoria implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private Set<AtributoCategoria> atributos = new HashSet<AtributoCategoria>(0);


	public Categoria() {
	}

	public Categoria(String nombre) {
		this.nombre = nombre;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "categoria")	
	public Set<AtributoCategoria> getAtributos() {
		return atributos;
	}

	public void setAtributos(Set<AtributoCategoria> atributos) {
		this.atributos = atributos;
	}
	
	

}
