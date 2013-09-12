<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Recursos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-museo.js" type="text/javascript"></script>		
		 ]]>
</jsp:text>
</head>
<body>
	<form style="width: 80%; border: 0px">
		<center>
			<h1>Gestionar Recursos</h1>
			<s:actionmessage id="algo" theme="jquery" />

			<table id="tblElemento">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Descripción</th>
						<th>Nombre del Archivo</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list">
						<tr>
							<td width="150">${nombre}</td>
							<td width="200">${descripcion}</td>
							<td width="200">${nombreArchivoFisico}</td>
							<td width="60"><a
								href="${pageContext.request.contextPath}/catalogo-recurso/${idRecurso}/edit"><img
									height="20" width="20" src="images/buttons/editar.png" /></a> <a
								href="${pageContext.request.contextPath}/catalogo-recurso/${idRecurso}/deleteConfirm"><img
									height="20" width="20" src="images/buttons/eliminar.png" /></a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

		</center>
	</form>
	<center>
		<br /> <a
			href="${pageContext.request.contextPath}/catalogo-recurso/new?idElementoSel=${idElementoSel}"><input
			type="submit" value="Nuevo Recurso"></input></a> <a
			href="${pageContext.request.contextPath}/catalogo-elemento?idExpoSel=${idExpoSel}"><input
			type="submit" value="Regresar"></input></a>
	</center>

</body>
	</html>
</jsp:root>
