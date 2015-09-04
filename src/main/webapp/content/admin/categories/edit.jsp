<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Categoria</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/admin/categories/js/edit.js"></script>	
]]>

</head>
<body>

	<h1>Modificar Categoria</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmCategoria" theme="simple"
		action="%{#pageContext.request.contextPath}/categories/%{idSel}" onsubmit="prepararEnvio()" method="post">		
		<div class="formulario">
			<div class="tituloFormulario">Información de la categoria</div>
			<div class="seccion">
			<table>
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="50"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
				<table id="tablaAtributo" class="tablaGestion" cellspacing="0"
					width="100%">
					<thead>
						<th style="width: 80%;"><s:text name="colAtributo" /></th>
						<th style="width: 20%;"><s:text name="colSeleccione" /></th>
					</thead>
					<tbody>
						<s:iterator value="listAtributos" var="atributo">
							<tr>
								<td><s:property value="%{#atributo.nombre}" /></td>
								<td align="center">
									<input id="checkbox-${atributo.nombre}" type ="checkbox"/>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</table>
			</div>
		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />
			<s:url var="urlGestionarCategorias"
				value="%{#pageContext.request.contextPath}/categories">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCategorias}'" value="Cancelar" />
		</div>
		
		<s:hidden name="_method" value="put" />
		<s:hidden id="jsonAtributosTabla" name="jsonAtributosTabla"
			value="%{jsonAtributosTabla}" />
	</s:form>
</body>
	</html>
</jsp:root>