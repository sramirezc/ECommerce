$(document).ready(function() {
	$('#gestion').DataTable();
	$('#tablaFiltroProducto').DataTable();
	$('#compraTabla').DataTable();
	contextPath = $("#rutaContexto").val();
	var jsonCategoriasSel = $("#jsonCategoriasSel").val();
	if (jsonCategoriasSel !== "") {
		var parsedJson = JSON.parse(jsonCategoriasSel);
		$.each(parsedJson, function(i, item) {
			document.getElementById("checkbox-" + item.nombre).checked = true;
		});
	}
} );

function prepararEnvio() {
	try {
		infoToJson("tablaFiltroProducto");
		return true;
	} catch (err) {
		alert("Ocurrió un error." + err);
	}
}

function infoToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloCategoriasSel = [];

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 0);
		seleccionado = document.getElementById("checkbox-" + nombre).checked;
		if (seleccionado == true) {
			arregloCategoriasSel.push(new Categoria(nombre));
		}
	}
	var jsonCategoriasSel = JSON.stringify(arregloCategoriasSel);

	document.getElementById("jsonCategoriasSel").value = jsonCategoriasSel;

}

function verMas(nbProducto){
	rutaMoreProducto = contextPath + '/customer!showDetails';
	$.ajax({
		dataType : 'json',
		url : rutaMoreProducto,
		type : "post",
		data : {
			searchProducto : nbProducto
		},
		success : function(data) {
			showDetails(data, nbProducto);
		},
		error : function(err) {
			alert("AJAX error: " + JSON.stringify(err, null, 2));
		}
	});
}

function showDetails(JSONProducto) {
	$("#atributosProducto").empty();
	if (JSONProducto != "") {
		$.each(JSONProducto, function(i, item) {
			var atributo = document.getElementById("detail_"+ item.atributoCategoria.atributo.nombre);
			if (atributo == null) {
				$("#atributosProducto").append(
						construirInput(item.atributoCategoria.atributo.nombre, item.valor));
			}
		});
		$('#detallesProducto').dialog('open');
	}
}

function construirInput(labelAtributo, valor) {
	var input = 
		"<div id = \"detail_"+labelAtributo+"\">" +
		"<tr>" +
		"<td><b>" +
		labelAtributo +
		": </b></td>" +
		"<td><i>" + 
		valor +					
		"</i></td>" + 
		"</tr>" +
		"</div>";
	return input;
}

function confirmarAgregarProducto(nbProducto) {
	$('#nbProductoAdded').val(nbProducto);
	$('#confirmarProducto').dialog('open');
}

function editarCantidad(nbProducto, cantProducto) {
	$('#nbProductoEdited').val(nbProducto);
	$('#cantProductoEdited').val(cantProducto);
	$('#editarProducto').dialog('open');
}

function actualizarProducto() {
	nbProducto = $('#nbProductoEdited').val();
	cantProducto = $('#cantProductoEdited').val();

	if(vaciaONula(nbProducto)) {
		alert("Agregue todos los campos obligatorios.");
		return false;
	}
	if(vaciaONula(cantProducto)) {
		alert("Ingrese una cantidad correcta");
		return false;
	}

	if(!isInteger(cantProducto)) {
		alert("Ingrese una cantidad correcta");
		return false;
	}



	rutaEditProducto = contextPath + '/customer!editProduct';
	$.ajax({
		dataType : 'json',
		url : rutaEditProducto,
		type : "post",
		data : {
			nbProductoSel : nbProducto,
			cantProductoSel : cantProducto
		},
		success : function(data) {
			alert("Cantidad actualizada");
			$("#frmBuscar").submit();
		},
		error : function(err) {
			alert("No hay suficientes productos, ingrese una cantidad menor.");
		}
	});

	return false;
}
function confirmarEliminarProducto (nbProducto) {
	$('#nbProductoDelete').val(nbProducto);
	$('#eliminarProducto').dialog('open');
}
function eliminarProducto() {
	nbProducto = $('#nbProductoDelete').val();
	rutaDeleteProducto = contextPath + '/customer!deleteProduct';
	$.ajax({
		dataType : 'json',
		url : rutaDeleteProducto,
		type : "post",
		data : {
			nbProductoSel : nbProducto,
		},
		success : function(data) {
			alert("Producto eliminado");
			$("#frmBuscar").submit();
		},
		error : function(err) {
			alert("No es posible realizar la compra debido a que no ha agregado ningún producto.");
		}
	});

	return false;
}

function agregarProducto() {
	nbProducto = $('#nbProductoAdded').val();
	cantProducto = $('#cantProductoAdded').val();

	if(vaciaONula(nbProducto)) {
		alert("Agregue todos los campos obligatorios.");
		return false;
	}
	if(vaciaONula(cantProducto)) {
		alert("Ingrese una cantidad correcta");
		return false;
	}

	if(!isInteger(cantProducto)) {
		alert("Ingrese una cantidad correcta");
		return false;
	}

	rutaAddProducto = contextPath + '/customer!addProduct';
	$.ajax({
		dataType : 'json',
		url : rutaAddProducto,
		type : "post",
		data : {
			nbProductoSel : nbProducto,
			cantProductoSel : cantProducto
		},
		success : function(data) {
			alert("Producto agregado");
			$("#frmBuscar").submit();
		},
		error : function(err) {
			alert("No hay suficientes productos, ingrese una cantidad menor.");
		}
	});

	return false;
}

function verCarrito() {
	$('#carritoDialog').dialog('open');
}
function cancelarConfirmar() {
	$('#confirmarProducto').dialog('close');
}
function cancelarEditar() {
	$('#editarProducto').dialog('close');
}
function cancelarEliminar() {
	$('#eliminarProducto').dialog('close');
}
function cerrarDetalle() {
	$('#detallesProducto').dialog('close');
}

function isInteger(str) {
    var n = ~~Number(str);
    return String(n) === str && n > 0;
}