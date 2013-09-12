<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Museos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-museo.js" type="text/javascript"></script>		
		 ]]>
</jsp:text>
</head>
<body>
	<form style="width: 80%; border: 0px">
		<center>
			<h1>Gestionar Museos</h1>
			<s:actionmessage id="algo" theme="jquery" />
			<table id="tblMuseo">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Descripción</th>
						<th>Dirección</th>
						<th>Contacto</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list">
						<tr>
							<td>${nombre}</td>
							<td>${descripcion}</td>
							<td>${calle}${numero} ${colonia} ${delegacion} ${estado}
								${codigo}</td>
							<td>${contacto}</td>
							<td>${edoMuseo}</td>
							<td width="160"><a
								href="${pageContext.request.contextPath}/catalogo-museo/${idMuseo}/edit.action"><img
									height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/botEditar2.png"
									title="Modificar Museo" /></a> <a
								href="${pageContext.request.contextPath}/catalogo-exposicion.action?idMuseoSel=${idMuseo}">
									<img height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/exposiciones.gif"
									title="Ver Exposiciones" />
							</a> <a
								href="${pageContext.request.contextPath}/catalogo-logro.action?idMuseoSel=${idMuseo}">
									<img height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/trofeo.png"
									title="Ver Logros" />
							</a></td>
						</tr>
					</s:iterator>
				</tbody>

			</table>
		</center>
	</form>
	<center>
		<a href="${pageContext.request.contextPath}/catalogo-museo/new.action"><input
			type="submit" value="Nuevo Museo"></input></a> <a
			href="${pageContext.request.contextPath}/catalogo-recurso/new.action"><input
			type="submit" value="Colecciones"></input></a>
	</center>

</body>
	</html>
</jsp:root>
