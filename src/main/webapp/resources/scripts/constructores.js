function Atributo (nombre) {
	this.nombre = nombre;
}

function Valor (nbAtributo, valor) {
	this.valor = valor;
	this.atributoCategoria = new AtributoCategoria(nbAtributo)
}

function Categoria (nombre) {
	this.nombre = nombre;
}

function AtributoCategoria (nbAtributo) {
	this.atributo = new Atributo(nbAtributo);
}

function Producto (nombre, cantidad, precio) {
	this.nombre = nombre;
	this.cantidad = cantidad;
	this.precio = precio;
}

