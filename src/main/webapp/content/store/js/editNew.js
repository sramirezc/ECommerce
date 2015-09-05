var contextPath = "ecommerce";

$(document).ready(function() {
	window.scrollTo(0, 0);
	contextPath = $("#rutaContexto").val();
	$('table.tablaGestion').DataTable();

	var jsonCategorias = $("#jsonCategorias").val();
	if (jsonCategorias !== "") {
		var parsedJson = JSON.parse(jsonCategorias);
		$.each(parsedJson, function(i, item) {
			var categoria = item.nombre;
			document.getElementById(categoria).checked = true;
			getInputs(categoria);
		});
	}
	
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
		$.each(JSONAtributos,
				function(i, item) {
			var atributo = document.getElementById("frmProductoAt_"
					+ item.nombre);
			var categoria = document.getElementById("tittle"
					+ nombreCategoria);

			if (categoria == null) {
				$("#atributos").append(
						"<div id = \"tittle" + nombreCategoria
						+ "\" class = \"tituloFormulario\" >"
						+ nombreCategoria + "</div>");
				$("#atributos").append(
						"<div id = \"section" + nombreCategoria
						+ "\" class = \"seccion\" ></div>");

			}
			if (atributo == null) {
				$("#section" + nombreCategoria).append(
						construirInput(item.nombre));
				setValue(item.nombre);
			}
			
			if ($("#section" + nombreCategoria).children().length == 0) {
				destroyInputs(nombreCategoria);
			}
		});
	}
}


function setValue(readyInput){
	var jsonValores = $("#jsonValores").val();
	if (jsonValores !== "") {
		var parsedJson = JSON.parse(jsonValores);
		$.each(parsedJson, function(i, item) {
			var valor = item.valor
			var nbAtributo = item.atributoCategoria.atributo.nombre;
			if (nbAtributo == readyInput){
				document.getElementById("frmProductoAt_" + nbAtributo).value = valor;
			}

		});
	}
}

function destroyInputs(nombreCategoria) {
	$("#tittle" + nombreCategoria).remove();
	$("#section" + nombreCategoria).remove();
}

function construirInput(atributo) {
	var input = "<tr>" + "<td class = \"label obligatorio\">" + atributo
	+ "</td>" + "<td>" + "<input id = \"frmProductoAt_" + atributo
	+ "\" class = \"inputFormulario ui-widget\" type = \"text\" "
	+ "value = \"\" maxlength = \"50\" name = \"" + atributo + "\"/>"
	+ "</td>" + "</tr>";
	return input;
}

function agregarMensaje(mensaje) {
	alert(mensaje);
};

function prepararEnvio() {
	try {
		infoToJson("tablaCategoria");
		return true;
	} catch (err) {
		alert("Ocurrió un error." + err);
	}
}

function infoToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloValores = [];
	var arregloCategorias = [];

	var atributosHTML = $('[id^=frmProductoAt_]');
	$.each(atributosHTML, function(i, item) {
		arregloValores.push(new Valor(item.name, item.value));
	});

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 0);
		seleccionado = document.getElementById(nombre).checked;
		if (seleccionado == true) {
			arregloCategorias.push(new Categoria(nombre));
		}
	}
	var jsonValores = JSON.stringify(arregloValores);
	var jsonCategorias = JSON.stringify(arregloCategorias);

	document.getElementById("jsonValores").value = jsonValores;
	document.getElementById("jsonCategorias").value = jsonCategorias;

}
