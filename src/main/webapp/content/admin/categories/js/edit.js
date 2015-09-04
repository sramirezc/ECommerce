var contextPath = "prisma";

$(document)
.ready(
		function() {
			window.scrollTo(0, 0);
			contextPath = $("#rutaContexto").val();
			$('table.tablaGestion').DataTable();					
			var json = $("#jsonAtributosTabla").val();
			if (json !== "") {
				var parsedJson = JSON.parse(json);
				$.each(parsedJson, function(i, item) {
					var nombre = item.nombre;
					document.getElementById("checkbox-" + nombre).checked = true;
				});
			}
			ocultarColumnas("tablaAtributo");
		});

function agregarMensaje(mensaje) {
	alert(mensaje);
};



function ocultarColumnas(tabla) {
	var dataTable = $("#" + tabla).dataTable();

}

function prepararEnvio() {
	try {
		tablaToJson("tablaAtributo");
		return true;
	} catch (err) {
		alert("Ocurrió un error.");
	}
}

function tablaToJson(idTable) {
	var table = $("#" + idTable).dataTable();
	var arregloAtributos = [];
	var seleccionado;

	for (var i = 0; i < table.fnSettings().fnRecordsTotal(); i++) {
		var nombre = table.fnGetData(i, 0);
		seleccionado = document.getElementById("checkbox-" + nombre).checked;
		if (seleccionado == true) {
			arregloAtributos.push(new Atributo(nombre));
		}
	}
	var jsonAtributos = JSON.stringify(arregloAtributos);
	document.getElementById("jsonAtributosTabla").value = jsonAtributos;
}
