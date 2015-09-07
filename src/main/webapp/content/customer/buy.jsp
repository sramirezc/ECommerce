<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Comprar productos</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/validaciones.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/scripts/constructores.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/customer/js/buy.js"></script>
]]>
</head>


<body>
	<h1>
		<a onclick="verCarrito()"><img id="" class="button"
			title="Ver carrito"
			src="${pageContext.request.contextPath}/resources/images/icons/miCarrito.png" /></a>Comprar
		productos
	</h1>
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror fieldName="errorBuy" cssClass="error"
								theme="jquery" />
	<br />
	<p class="instrucciones">Ingrese la siguiente información para
		buscar productos. Si no ingresa ningún campo se mostrarán todos los
		productos registrados</p>
	<div class="formulario">
		<s:form autocomplete="off" theme="simple" id = "frmBuscar"
			action="%{pageContext.request.contextPath}/customer!search"
			onsubmit="prepararEnvio()">
			<div class="tituloFormulario">Buscar productos</div>
			<div class="seccion">
				<table>
					<tr>
						<td class="label obligatorio"><s:text name="labelNombre" /></td>
						<td><s:textfield name="searchProducto" maxlength="200"
								cssErrorClass="input-error" cssClass="inputFormulario ui-widget" />
							<s:fielderror fieldName="searchProducto" cssClass="error"
								theme="jquery" /></td>
					</tr>

					<table id="tablaFiltroProducto" class="tablaGestion"
						cellspacing="0" width="100%">
						<thead>
							<th style="width: 80%;"><s:text name="colCategoria" /></th>
							<th style="width: 20%;"><s:text name="colSeleccione" /></th>
						</thead>
						<tbody>
							<s:iterator value="listCategorias" var="categoria">
								<tr>
									<td><s:property value="%{#categoria.nombre}" /></td>
									<td align="center"><input
										id="checkbox-${categoria.nombre}" type="checkbox" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</table>
			</div>
			<div align="center">
				<s:submit class="boton" value="Buscar" />

			</div>
			<br />
			<br />

			<s:hidden id="jsonCategoriasSel" name="jsonCategoriasSel"
				value="%{jsonCategoriasSel}" />
		</s:form>
		
		<s:form autocomplete="off" theme="simple" onsubmit="return false;">
		<div class="tituloFormulario">Resultados</div>
		<div class="seccion">
			<table id="gestion" class="tablaGestion" cellspacing="0" width="100%">
				<thead>
					<th style="width: 70%;"><s:text name="colProducto" /></th>
					<th style="width: 20%;"><s:text name="colDisponible" /></th>
					<th style="width: 20%;"><s:text name="colPrecio" /></th>					
					<th style="width: 10%;"><s:text name="colAcciones" /></th>
				</thead>
				<tbody>
					<s:iterator value="listProductos" var="producto">
						<tr>
							<td><s:property value="%{#producto.nombre}" /></td>
							<td align="right"><s:property value="%{#producto.cantidad}" /></td>
							<td align="right"><s:property value="%{#producto.precio}" /></td>
							<td align="center">
							<a onclick="verMas('${producto.nombre}')"><img id="" class="button"
									title="Detalles"
									src="${pageContext.request.contextPath}/resources/images/icons/detail.png" /></a>
									${blanks}
								<s:if test="#producto.cantidad > 0">
									<a onclick="confirmarAgregarProducto('${producto.nombre}')"><img id="" class="button"
									title="Ver carrito"
									src="${pageContext.request.contextPath}/resources/images/icons/buy.png" /></a>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<br /> <br />
		</s:form>
	</div>
	<!-- Detalles producto -->
	<sj:dialog id="detallesProducto" title="Detalles" autoOpen="false"
		minHeight="150" minWidth="200" modal="true" draggable="false">
		<s:form autocomplete="off" id="frmDetallesProducto" name="frmDetallesProducto" theme="simple">
				<div class="seccion" align="left">
				<s:text name="Información del producto:"></s:text>
				<br/>
				<br/>
				<table>
					<div id = "atributosProducto">
					</div>
				</table>
				</div>
			<br />
			<div align="center">
				<input id = "btnConfirmar" type="button" onclick="cerrarDetalle()" value="Aceptar"/> 
			</div>
		</s:form>
	</sj:dialog>
	
	<!-- Confirmar producto -->
	<sj:dialog id="confirmarProducto" title="Confirmación" autoOpen="false"
		minHeight="150" minWidth="500" modal="true" draggable="false">
		<s:form autocomplete="off" id="frmConfirmar" name="frmConfirmar" theme="simple">
				<div class="seccion">
				<s:text name="Para continuar ingrese la cantidad y oprima el botón aceptar."></s:text>
				<br/>
				<table>
					<tr>
						<td class="label obligatorio"><s:text name="labelProducto" /></td>
						<td>
							<input id="nbProductoAdded" class="inputFormulario ui-widget" type="text" value="" maxlength="200" name="productoAdded" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelProducto" /></td>
						<td>
							<input id="cantProductoAdded" class="inputFormulario ui-widget" type="text" maxlength="10"  name="cantProductoAdded"/>
						</td>
					</tr>
				</table>
				</div>
			<br />
			<div align="center">
				<input id = "btnConfirmar" type="button" onclick="agregarProducto()" value="Aceptar"/> 
				<input type="button" onclick="cancelarConfirmar();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
	
	<!-- Finalizar compra -->
	<sj:dialog id="carritoDialog" title="Carrito" autoOpen="false"
		minHeight="40" minWidth="700" modal="true" draggable="true">
		<s:form autocomplete="off" id="frmCarrito" name="frmCarrito"
			theme="simple" action="customer!finishBuy">
			<div class="seccion">
				<table id = "compraTabla" class="tablaGestion">
					<thead>
						<th style="width: 80%;">Producto</th>
						<th style="width: 20%;">Cantidad</th>
						<th style="width: 20%;">Total</th>
						<th style="width: 20%;">Acciones</th>
					</thead>
					<tbody>
						<s:iterator value="listCompraProducto" var="compraProducto">
							<tr>
								<td><s:property value="%{#compraProducto.producto.nombre}" /></td>
								<td align="right"><s:property value="%{#compraProducto.cantidad}" /></td>
								<td align="right"><s:property value="%{#compraProducto.cantidad * #compraProducto.producto.precio}" /></td>
								<td align="center">
									<a onclick="verMas('${compraProducto.producto.nombre}')"><img id="" class="button"
									title="Detalles"
									src="${pageContext.request.contextPath}/resources/images/icons/detail.png" /></a>
									${blanks}
									<a onclick="editarCantidad('${compraProducto.producto.nombre}', '${compraProducto.cantidad}')"><img id="" class="button"
									title="Editar cantidad"
									src="${pageContext.request.contextPath}/resources/images/icons/editar.png" /></a>
									${blanks}
									<a onclick="confirmarEliminarProducto('${compraProducto.producto.nombre}')"><img id="" class="button"
									title="Editar cantidad"
									src="${pageContext.request.contextPath}/resources/images/icons/eliminar.png" /></a>
							</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<br />
			<div align="center">
				<input type="submit" value="Comprar" />
			</div>
		</s:form>
	</sj:dialog>
	
	<!-- Editar cantidad -->
	<sj:dialog id="editarProducto" title="Confirmación" autoOpen="false"
		minHeight="150" minWidth="500" modal="true" draggable="false">
		<s:form autocomplete="off" id="frmEditar" name="frmEditar" theme="simple">
				<div class="seccion">
				<s:text name="Para continuar ingrese la cantidad y oprima el botón aceptar."></s:text>
				<br/>
				<table>
					<tr>
						<td class="label obligatorio"><s:text name="labelProducto" /></td>
						<td>
							<input id="nbProductoEdited" class="inputFormulario ui-widget" type="text" value="" maxlength="200" name="productoAdded" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="label obligatorio"><s:text name="labelProducto" /></td>
						<td>
							<input id="cantProductoEdited" class="inputFormulario ui-widget" type="number" value="" min = "0" max = "" name="cantProductoAdded"/>
						</td>
					</tr>
				</table>
				</div>
			<br />
			<div align="center">
				<input id = "btnConfirmar" type="button" onclick="actualizarProducto()" value="Aceptar"/> 
				<input type="button" onclick="cancelarEditar();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>
	
	<!-- Confirmar eliminación -->
	<sj:dialog id="eliminarProducto" title="Confirmación" autoOpen="false"
		minHeight="150" minWidth="500" modal="true" draggable="false">
		<s:form autocomplete="off" id="frmEliminar" name="frmEliminar" theme="simple">
				<div class="seccion">
				<s:text name="¿Está seguro que desea eliminar este producto de su carrito?"></s:text>
				<br/>
				<input id="nbProductoDelete" value ="" type = "text" hidden="true"/>
				</div>
			<br />
			<div align="center">
				<input id = "btnConfirmar" type="button" onclick="eliminarProducto()" value="Aceptar"/> 
				<input type="button" onclick="cancelarEliminar();" value="Cancelar" />
			</div>
		</s:form>
	</sj:dialog>

</body>
	</html>
</jsp:root>

