var contextPath = "ecommerce";

$(document).ready(function() {
	window.scrollTo(0, 0);
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();
	/*
	 * var json = $("#jsonAtributosTabla").val(); if (json !== "") { var
	 * parsedJson = JSON.parse(json); $.each(parsedJson, function(i, item) { var
	 * nombre = item.nombre; document.getElementById("checkbox-" +
	 * nombre).checked = true; }); } ocultarColumnas("tablaCategoria");
	 */
});

function getInputs(nombreCategoria) {
	var isCategoriaChecked = document.getElementById(nombreCategoria).checked;
	if (isCategoriaChecked == true) {
	rutaFindAttributes = contextPath + '/store!findAttributes';
	$.ajax({
		dataType : 'json',
		url : rutaFindAttributes,
		type : "post",
		data : {
			nbCategoriaSel : nombreCategoria
		},
		success : function(data) {
			showInputs(data, nombreCategoria);
		},
		error : function(err) {
			alert("AJAX error: " + JSON.stringify(err, null, 2));
		}
	});
	} else {
		destroyInputs(nombreCategoria);
	}
	return false;
}

function showInputs(JSONAtributos, nombreCategoria) {
	if (JSONAtributos != "") {
		$.each(JSONAtributos, function(i, item) {
			var atributo = document.getElementById("frmProductoAt_" + item.nombre);
			var categoria = document.getElementById("tittle"+nombreCategoria);
			
			if (categoria == null) {
				$("#atributos").append("<div id = \"tittle" + nombreCategoria + "\" class = \"tituloFormulario\" >"+nombreCategoria+"</div>");
				$("#atributos").append("<div id = \"section" + nombreCategoria + "\" class = \"seccion\" ></div>");

			}
			if (atributo == null) {
				$("#section" + nombreCategoria).append(construirInput(item.nombre));
			}
		});
	}
}
/*verificar que el atributo en la sección general*/
function destroyInputs(nombreCategoria) {
	$("#tittle" + nombreCategoria).remove();
	$("#section" + nombreCategoria).remove();
}

function construirInput(atributo) {
	var input = "<tr>" +
					"<td class = \"label obligatorio\">" +
						atributo+
					"</td>" +
					"<td>" +
						"<input id = \"frmProductoAt_" + atributo + "\" class = \"inputFormulario ui-widget\" type = \"text\" " +
								"value = \"\" maxlength = \"50\" name = \"" + atributo + "\"/>"+
					"</td>" +
				"</tr>";	
	return input;
}

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();

}

function prepararEnvio() {
	try {
		infoToJson("tablaCategoria");
		return true;
	} catch (err) {
		alert("Ocurrió un error.");
	}
}

function infoToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloAtributos = [];
	var arregloCategorias = [];
	
	var atributosHTML = $('[id^=frmProductoAt_]');
	$.each(atributosHTML, function(i, item) {
		arregloAtributos.push(new Atributo(item.value));
	});

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 0);
		seleccionado = document.getElementById(nombre).checked;
		if (seleccionado == true) {
			arregloCategorias.push(new Categoria(nombre));
		}
	}
	var jsonAtributos = JSON.stringify(arregloAtributos);
	var jsonCategorias = JSON.stringify(arregloCategorias);
	
	document.getElementById("jsonAtributos").value = jsonAtributos;
	document.getElementById("jsonCategorias").value = jsonCategorias;

}
