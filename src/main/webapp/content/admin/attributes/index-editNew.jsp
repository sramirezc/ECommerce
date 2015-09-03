<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Atributo</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/admin/attributes/js/index-editNew.js"></script>	
]]>

</head>
<body>

	<h1>Registrar Atributo</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" id="frmAtributo" theme="simple"
		action="%{#pageContext.request.contextPath}/attributes" method="post">
		<div class="formulario">
			<div class="tituloFormulario">Información del Atributo</div>
			<table class="seccion">
				<tr>
					<td class="label obligatorio"><s:text name="labelNombre" /></td>
					<td><s:textfield name="model.nombre" maxlength="50"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName ="model.nombre" cssClass="error"
							theme="jquery" /></td>
				</tr>
			</table>
		</div>
		
		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />

			<s:url var="urlGestionarAtributos"
				value="%{#pageContext.request.contextPath}/attributes">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarAtributos}'"
				value="Cancelar" />
		</div>
	</s:form>
</body>
	</html>
</jsp:root>