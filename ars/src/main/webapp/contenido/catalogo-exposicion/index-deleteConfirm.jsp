<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Exposición</title>
</head>
<body>

	<s:url id="urlCancelar" value="/catalogo-exposicion"
		includeContext="true" />
	<s:actionerror id="saeExposicion" theme="jquery" />
	<s:fielderror id="sfeExposicion" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-exposicion/%{idExposicion}"
		method="post" theme="simple" acceptcharset="UTF-8"
		cssStyle="border: 0px">
		<h1>Eliminar Exposición</h1>
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<label>Nombre:</label>
		<s:textfield id="Nombre" name="model.nombre" maxlength="50"
			disabled="true" />
		<br />
		<label>Descripción:</label>
		<br />
		<s:textarea rows="3" cols="70" id="Descripcion"
			name="model.descripcion" maxlength="200" disabled="true" />
		<br />
		<table id="tblMuseo">
			<thead>
				<tr>
					<th>Subtemas</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="model.subtemas">
					<tr>
						<td>${nombre}</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
			buttonIcon="ui-icon-star" />
		<sj:a id="btnCancelar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>

</body>
	</html>
</jsp:root>