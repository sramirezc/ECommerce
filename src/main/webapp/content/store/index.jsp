<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Productos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/store/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Productos</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 80%;"><s:text name="colProducto"/></th>
				<th style="width: 20%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listProductos" var="producto">
				<tr>
					<td><s:property value="%{#producto.nombre}"/></td>

					
					<td align="center">
						${blanks}
						<!-- Modificar producto -->	
						<s:url var="urlModificar" value="%{#pageContext.request.contextPath}/store/%{#producto.id}/edit"/>			
						<s:a href="%{urlModificar}">
							<img id="" class="button" title="Modificar Producto"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
						</s:a>
						${blanks}		
						<!-- Eliminar producto -->			
						<s:url var="urlEliminar" value="%{#pageContext.request.contextPath}/store/%{#producto.id}?_method=delete" method="post"/>
						<s:a href="%{urlEliminar}">
						<img id="" class="button" title="Eliminar Producto"
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
			onclick="location.href='${pageContext.request.contextPath}/store/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
</body>
</html>
</jsp:root>

