<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Producto</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/store/js/edit.js"></script>	
]]>

</head>
<body>

	<h1>Registrar Producto</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmProducto" theme="simple"
		action="%{#pageContext.request.contextPath}/store/%{idSel}" onsubmit="prepararEnvio()" method="post">
		<s:fielderror fieldName="formError" cssClass="error" theme="jquery" />
		
		<div class="formulario">
			<div class="tituloFormulario">Información del Producto</div>
			<div class="seccion">
			<table>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="50"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName ="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
			<table id="tablaCategoria" class="tablaGestion" cellspacing="0"
					width="100%">
					<thead>
						<th style="width: 80%;"><s:text name="colCategoria" /></th>
						<th style="width: 20%;"><s:text name="colSeleccione" /></th>
					</thead>
					<tbody>
						<s:iterator value="listCategorias" var="categoria">
							<tr>
								<td><s:property value="%{#categoria.nombre}" /></td>
								<td align="center">
									<input id="${categoria.nombre}" onchange="getInputs('${categoria.nombre}')" type ="checkbox"/>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<br/>
				<div id = "atributos">
				<s:fielderror fieldName="atributos" cssClass="error" theme="jquery" />
				</div>
			</table>
			</div>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarProductos"
				value="%{#pageContext.request.contextPath}/store">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarProductos}'"
				value="Cancelar" />
				
		</div>
		
				
		<s:hidden id="jsonValores" name="jsonValores" value="%{jsonValores}" />
		<s:hidden id="jsonCategorias" name="jsonCategorias" value="%{jsonCategorias}" />
		<s:hidden name="_method" value="put" />
		
	</s:form>
</body>
	</html>
</jsp:root>