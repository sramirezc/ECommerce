<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Historial de compras</title>
<![CDATA[
]]>
</head>
<body>
	<h1>Historial de compras</h1>


		<table class="tablaGestion" >
			<s:iterator value="listCompras" var="compra">
			
				<tr class="tituloFormulario2" >
					<td>
						<s:property value="#compra.fecha" />
					</td>
					<td>
					</td>
					
				</tr>
				<s:iterator value="#compra.productos" var="compraProducto">
					<tr>
						<td><b><s:property
									value="#compraProducto.cantidad +' ' + #compraProducto.producto.nombre" /> (s)</b></td>
									<td>$ <s:property
									value="#compraProducto.cantidad * #compraProducto.producto.precio" /></td>
									

					</tr>
				</s:iterator>
			</s:iterator>
		</table>

</body>
	</html>
</jsp:root>

