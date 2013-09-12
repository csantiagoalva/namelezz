<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Subtema</title>
</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-subtema.action"
		includeContext="true" />
	<s:actionerror id="saeSubtema" theme="jquery" />
	<s:fielderror id="sfeSubtema" theme="jquery" />

	<s:form
		action="%{#request.contextPath}/catalogo-subtema/%{idSubtema}.action"
		method="post" theme="simple" acceptcharset="UTF-8"
		cssStyle="border: 0px;">
		<center>
			<h1>Eliminar Subtema</h1>
		</center>
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<table>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="Nombre" name="model.nombre" maxlength="50"
						disabled="true" /></td>
			</tr>
			<tr>
				<td colspan="2"><label>Descripción:</label></td>
			</tr>
			<tr>
				<td colspan="2"><s:textarea rows="5" cols="50" id="Descripcion"
						name="model.descripcion" maxlength="200" disabled="true" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><sj:submit
						id="btnAceptar" value="Aceptar" button="true"
						buttonIcon="ui-icon-star" /> <sj:a id="btnCancelar" button="true"
						href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
				</td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>