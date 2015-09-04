<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Actores</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/admin/categories/js/index.js"></script>
]]>
</head>

<body>
	<h1>Gestionar Categorias</h1>
	<s:actionmessage theme="jquery"/>
	<s:actionerror theme="jquery"/>
	
	<br/>
	<s:form autocomplete="off" theme="simple" onsubmit="return false;">
	<div class="form">
		<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
			<thead>
				<th style="width: 80%;"><s:text name="colCategoria"/></th>
				<th style="width: 20%;"><s:text name="colAcciones"/></th>
			</thead>
			<tbody>
			<s:iterator value="listCategorias" var="categoria">
				<tr>
					<td><s:property value="%{#categoria.nombre}"/></td>

					
					<td align="center">
						${blanks}
						<!-- Modificar categoria -->	
						<s:url var="urlModificar" value="%{#pageContext.request.contextPath}/categories/%{#categoria.id}/edit"/>			
						<s:a href="%{urlModificar}">
							<img id="" class="button" title="Modificar Categoria"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" />
						</s:a>
						${blanks}		
						<!-- Eliminar categoria -->			
						<s:url var="urlEliminar" value="%{#pageContext.request.contextPath}/categories/%{#categoria.id}?_method=delete" method="post"/>
						<s:a href="%{urlEliminar}">
						<img id="" class="button" title="Eliminar Categoria"
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
			onclick="location.href='${pageContext.request.contextPath}/categories/new'">
			<s:text name="Registrar"></s:text>
		</button>
	</div>
	</s:form>
</body>
</html>
</jsp:root>

