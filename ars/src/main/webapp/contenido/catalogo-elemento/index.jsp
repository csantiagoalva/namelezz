<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Elementos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-elemento.js" type="text/javascript"></script>		
		 ]]>
</jsp:text>
</head>
<body>
	<form style="width: 80%; border: 0px">
		<center>
			<h1>Gestionar Elementos</h1>
			<s:actionmessage id="algo" theme="jquery" />

			<table id="tblElemento">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Descripción</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list">
						<tr>
							<td width="200">${nombre}</td>
							<td width="600">${descripcion}</td>
							<td width="90"><a
								href="${pageContext.request.contextPath}/catalogo-elemento/${idElemento}/edit.action"><img
									height="40" width="40" src="images/buttons/botEditar2.png"
									title="Modificar Elemento" /></a> <a
								href="${pageContext.request.contextPath}/catalogo-elemento/${idElemento}/deleteConfirm.action"><img
									height="40" width="40" src="images/buttons/eliminar.png"
									title="Eliminar Elemento" /></a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</center>
	</form>
	<center>
		<br /> <a
			href="${pageContext.request.contextPath}/catalogo-elemento/new.action?idExpoSel=${idExpoSel}"><input
			type="submit" value="Nuevo Elemento" size="200px"></input></a> <a
			href="${pageContext.request.contextPath}/catalogo-exposicion.action?idMuseoSel=${idMuseoSel}"><input
			type="submit" value="Regresar"></input></a>
		<s:a href="/ars/download.action">
			<input type="submit" value="Descargar Imagenes"></input>
		</s:a>
	</center>
</body>
	</html>
</jsp:root>
