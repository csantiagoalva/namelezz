<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Reconocimiento de Museos</title>
<jsp:text>
	<![CDATA[ 			
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-museo.action"
		includeContext="true" />
	<s:actionerror id="saeElemento" theme="jquery" />
	<s:fielderror id="sfeElemento" theme="jquery" />

	<s:form action="%{#request.contextPath}/catalogo-recurso.action"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmRecurso"
		enctype="multipart/form-data" cssStyle="border: 0px">
		<center>
			<h1>Reconocimiento de Museos</h1>

			<table>
				<tr>
					<td><label>Archivo .dat:</label></td>
					<td><s:file name="archivoDat" /></td>
				</tr>
				<tr>
					<td><label>Archivo .xml:</label></td>
					<td><s:file name="archivoXml" /></td>
				</tr>
				<tr>
					<td colspan="2"><s:a href="/ars/download.action">
							<input type="button" value="Descargar Imagenes"></input>
						</s:a></td>
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