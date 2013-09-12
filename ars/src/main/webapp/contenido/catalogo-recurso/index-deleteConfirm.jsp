<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Recurso</title>
<jsp:text>
	<![CDATA[ 			
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar"
		value="/catalogo-recurso?idElementoSel=%{elemento.idElemento}"
		includeContext="true" />
	<s:actionerror id="saeTipoUnidad" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-recurso/%{idRecurso}?idElementoSel=%{elemento.idElemento}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmRecurso"
		enctype="multipart/form-data">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<center>
			<h1>Eliminar Recurso</h1>
			<table>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="Nombre" name="model.nombre"
							maxlength="60" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Descripción:</label></td>
					<td><s:textarea cols="60" rows="5" id="Descripcion"
							name="model.descripcion" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Archivo:</label></td>
					<td><s:file name="archivoEvidencia" label="Archivo:" /></td>
				</tr>
				<tr>
					<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
							button="true" buttonIcon="ui-icon-star" /> <sj:a
							id="btnCancelar" button="true" buttonIcon="ui-icon-bullet"
							href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
				</tr>
			</table>
		</center>
	</s:form>
</body>
	</html>
</jsp:root>