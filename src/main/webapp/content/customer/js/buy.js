$(document).ready(function() {
	$('#gestion').DataTable();
	$('#tablaFiltroProducto').DataTable();
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

function confirmarProducto (nbProducto) {
	$('#confirmarProducto').dialog('open');
}

function agregarProducto(nbProducto) {
		
		rutaAddProducto = contextPath + '/customer!addProduct';
		$.ajax({
			dataType : 'json',
			url : rutaAddProducto,
			type : "post",
			data : {
				nbProductoSel : nbProducto
			},
			success : function(data) {
				addInputs(data, nbProducto);
			},
			error : function(err) {
				alert("AJAX error: " + JSON.stringify(err, null, 2));
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
