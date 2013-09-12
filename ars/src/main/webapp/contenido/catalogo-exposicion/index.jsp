<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Exposiciones</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-exposicion.js" type="text/javascript"></script>		
		 ]]>
</jsp:text>
</head>
<body>
	<form style="width: 80%; border: 0px">
		<center>
			<h1>Gestionar Exposiciones</h1>
			<s:actionmessage id="algo" theme="jquery" />
			<table id="tblExposicion">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Descripción</th>
						<th>Rank</th>
						<th>Tipo</th>
						<th>Inicio</th>
						<th>Fin</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>

					<s:iterator value="list">
						<tr>
							<td>${nombre}</td>
							<td>${descripcion}</td>
							<td>${ranking}</td>
							<td>${tipo}</td>
							<td>${fhInicio}</td>
							<td>${fhFin}</td>
							<td>${edoExposicion}</td>
							<td width="90"><a
								href="${pageContext.request.contextPath}/catalogo-exposicion/${idExposicion}/edit.action"><img
									height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/botEditar2.png"
									title="Modificar Exposición" /></a> <a
								href="${pageContext.request.contextPath}/catalogo-elemento.action?idExpoSel=${idExposicion}"><img
									height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/Elemento.jpg"
									title="Ver Elementos" /></a></td>
						</tr>
					</s:iterator>
				</tbody>

			</table>
		</center>
	</form>
	<center>
		<a
			href="${pageContext.request.contextPath}/catalogo-exposicion/new.action"><input
			type="submit" value="Agregar Exposicion"></input></a> <a
			href="${pageContext.request.contextPath}/catalogo-museo.action"><input
			type="submit" value="Regresar"></input></a>

	</center>
</body>
	</html>
</jsp:root>

