<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Iniciar sesión</title>
<![CDATA[
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquery.realperson.css" />
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/jquery.plugin.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/jquery.realperson.js"></script>
<script>
$(function() {
	$('#captcha').realperson();
});
</script>
]]>
</head>
<body>
	<h1>Iniciar sesión</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" theme="simple"
		action="%{pageContext.request.contextPath}/access!login">
		<div class="formulario">
			<div class="tituloFormulario">Iniciar sesión</div>
			<table class="seccion">

				<tr>
					<td class="label obligatorio"><s:text name="labelCorreo" /></td>
					<td><s:textfield name="userName" maxlength="50"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="userName" cssClass="error" theme="jquery" /></td>
				</tr>
				<tr>
					<td class="label obligatorio"><s:text name="labelContrasenia" /></td>
					<td><s:password name="password" maxlength="20"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="password" cssClass="error" theme="jquery" /></td>
				</tr>
				<br/>
				<tr><td class="label obligatorio"><s:text name="labelCaptcha" /></td>
					<td><s:textfield name="captcha" id="captcha" maxlength="10"
							cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
						<s:fielderror fieldName="captcha" cssClass="error" theme="jquery" /></td>
				</tr>
			</table>
			<div align="right">
				<a class="link"
					href="${pageContext.request.contextPath}/access!recover">Recuperar contraseña</a>

			</div>
			<div align="right">
				<a class="link"  formmethod="get"
					href="${pageContext.request.contextPath}/customer/new">Registrarse</a>

			</div>

		</div>

		<br />
		<div align="center">
			<s:submit class="boton" value="Aceptar" />
		</div>
	</s:form>
</body>
	</html>
</jsp:root>

