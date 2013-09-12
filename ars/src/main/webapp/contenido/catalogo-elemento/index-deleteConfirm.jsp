<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Elemento</title>
<jsp:text>
	<![CDATA[
	<script src="${pageContext.request.contextPath}/scripts/catalogo-elemento.js" type="text/javascript"></script> 			
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar"
		value="/catalogo-elemento.action?idExpoSel=%{exposicion.idExposicion}"
		includeContext="true" />
	<s:actionerror id="saeTipoUnidad" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-elemento/%{idElemento}.action?idExpoSel=%{exposicion.idExposicion}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmElemento"
		style="border: 0px">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<center>
			<h1>Eliminar Elemento</h1>
			<table>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="Nombre" name="model.nombre"
							maxlength="60" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Descripción:</label></td>
					<td><s:textarea cols="60" rows="15" id="Descripcion"
							name="model.descripcion" disabled="true" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<h3>Recursos</h3>
						<table>

							<tbody>
								<s:iterator value="listRecurso">
									<tr>
										<td width="200">${nombreArchivoFisico}</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>

					</td>
				</tr>

			</table>
			<sj:submit id="btnAceptar" value="Aceptar" button="true"
				buttonIcon="ui-icon-star" />
			<sj:a id="btnCancelar" button="true" buttonIcon="ui-icon-bullet"
				href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</center>
	</s:form>
</body>
	</html>
</jsp:root>