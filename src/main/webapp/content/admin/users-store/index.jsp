<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Personal del Almacén</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/admin/users-store/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Personal del Almacén</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 80%;"><s:text name="colUsuario"/></th>
				<th style="width: 20%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listUsuariosAlmacen" var="usuario">
				<tr>
					<td><s:property value="%{#usuario.nombre}"/>${" "}<s:property value="%{#usuario.apPaterno}"/>${" "}<s:property value="%{#usuario.apMaterno}"/></td>
		
					<td align="center">
						${blanks}
						<!-- Modificar usuario -->	
						<s:url var="urlModificar" value="%{#pageContext.request.contextPath}/users-store/%{#usuario.id}/edit"/>			
						<s:a href="%{urlModificar}">
							<img id="" class="button" title="Modificar Usuario"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
						</s:a>
						${blanks}		
						<!-- Eliminar usuario -->			
						<s:url var="urlEliminar" value="%{#pageContext.request.contextPath}/users-store/%{#usuario.id}?_method=delete" method="post"/>
						<s:a href="%{urlEliminar}">
						<img id="" class="button" title="Eliminar Usuario"
								src="${pageContext.request.contextPath}/resources/images/icons/eliminar.png" /></s:a>
						
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		
	</div>
	<br />
	<br />
	<div align="center">
		<button class="boton" formmethod="post"
			onclick="location.href='${pageContext.request.contextPath}/users-store/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
</body>
</html>
</jsp:root>

