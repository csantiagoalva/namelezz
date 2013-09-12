<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Logros</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-museo.js" type="text/javascript"></script>		
		 ]]>
</jsp:text>
</head>
<body>
	<form style="width: 80%; border: 0px">
		<center>
			<h1>Gestionar Logros</h1>
			<s:actionmessage id="algo" theme="jquery" />
			<table id="tblMuseo">
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
							<td>${nombre}</td>
							<td>${descripcion}</td>
							<td width="90"><a
								href="${pageContext.request.contextPath}/catalogo-logro/${idLogro}/edit.action?idMuseoSel=${idMuseoSel}"><img
									height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/botEditar2.png"
									title="Modificar Logro" /></a> <a
								href="${pageContext.request.contextPath}/catalogo-logro/${idLogro}/deleteConfirm.action?idMuseoSel=${idMuseoSel}"><img
									height="40" width="40"
									src="${pageContext.request.contextPath}/images/buttons/eliminar.png"
									title="Eliminar Logro" /></a></td>
						</tr>
					</s:iterator>
				</tbody>

			</table>
		</center>
	</form>
	<center>
		<a
			href="${pageContext.request.contextPath}/catalogo-logro/new.action?idMuseoSel=${idMuseoSel}"><input
			type="submit" value="Nuevo Logro"></input></a> <a
			href="${pageContext.request.contextPath}/catalogo-museo.action"><input
			type="submit" value="Regresar"></input></a>
	</center>

</body>
	</html>
</jsp:root>
