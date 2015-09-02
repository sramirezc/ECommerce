<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags">
	
	<jsp:directive.page language="java"
		contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>ECommerce | <decorator:title default="Bienvenido"/></title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilo.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/DataTables-1.10.7/media/css/jquery.dataTables.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.atwho.css" />
<s:url id="urlRutaContexto" includeParams="none"
	value="%{pageContext.request.contextPath}/resources/template/themes"
	includeContext="true" />
<sj:head debug="false" jqueryui="true" jquerytheme="smoothness-prisma"
	customBasepath="%{urlRutaContexto}" locale="es" />

<decorator:head />
<![CDATA[
	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/DataTables-1.10.7/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/menu.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/dataTable.js"></script>
]]>

</head>
<body>
	<div class="container">
		<div class="banner"><jsp:include page="/resources/decorators/banner.jsp" /></div>
		<div class="menuPrincipal"><jsp:include page="/content/menus/menuProyecto.jsp" /></div>
		<div class="menuSecundario">
			<jsp:include page="/resources/decorators/menus/menuSecundario.jsp" />
		</div>
		<div class="areaTrabajo" id ="idAreaTrabajo">
			<decorator:body /> 
		</div>
	</div>
	<input type="text" style="display: none;" id="rutaContexto"
 		value="${pageContext.request.contextPath}" />
 		
 	<s:if test="#session != null">
 		<s:param value="#session.correo"/>
 	</s:if>
 	
</body>
</html>
</jsp:root>

