<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cliente</title>
<![CDATA[
]]>
</head>
<body>
	<h1>Bienvenido (a) <s:property value = "%{usuario.nombre}"/></h1>

	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />

</body>
	</html>
</jsp:root>

