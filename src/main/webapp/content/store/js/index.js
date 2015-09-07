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

function verMas(nbProducto){
	rutaMoreProducto = contextPath + '/store!showDetails';
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

function cerrarDetalle() {
	$('#detallesProducto').dialog('close');
}

