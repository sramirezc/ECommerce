<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Iniciar sesión</title>
<![CDATA[
]]>
</head>
<body>
	<h1>Bienvenido (a)</h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

	<p class="instrucciones">Ingrese la información solicitada.</p>
	<s:form autocomplete="off" theme="simple"
		action="%{pageContext.request.contextPath}/access!login">
		<div class="formulario">
			<div class="tituloFormulario">Iniciar sesión</div>
			<table class="seccion">
			</table>
			<div align="right">
				<a class="link"
					href="${pageContext.request.contextPath}/access!recover">Recuperar
					contraseña</a>

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

