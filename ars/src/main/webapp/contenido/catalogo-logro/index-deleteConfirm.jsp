<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Logro</title>
<jsp:text>
	<![CDATA[			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/seleccionar-elementos.js" >
			</script>			
		 ]]>
</jsp:text>
</head>
<body>

	<s:url id="urlCancelar"
		value="/catalogo-logro.action?idMuseoSel=%{idMuseoSel}"
		includeContext="true" />
	<s:actionerror id="saeLogro" theme="jquery" />
	<s:fielderror id="sfeLogro" theme="jquery" />


	<s:form
		action="%{#request.contextPath}/catalogo-logro/%{idLogro}.action?idMuseoSel=%{idMuseoSel}"
		method="post" theme="simple" acceptcharset="UTF-8" align="center"
		id="frmLogro" cssStyle="border: 0px" enctype="multipart/form-data">
		<center>
			<h1>Eliminar Logro</h1>
			<s:hidden id="hdnMethod" name="_method" value="delete" />
			<table>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="Nombre" name="model.nombre"
							maxlength="100" size="60" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Descripción:</label></td>

					<td><s:textarea rows="4" cols="60" id="Descripcion"
							name="model.descripcion" maxlength="200" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Elementos:</label></td>
					<td>
						<table>
							<s:iterator value="elementosLogro">
								<tr>
									<td>${nombre}</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>


			</table>

			<sj:submit id="btnAceptar" value="Aceptar" button="true"
				buttonIcon="ui-icon-star" />
			<sj:a id="btnCancelar" button="true" href="#"
				onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</center>

	</s:form>

</body>
	</html>
</jsp:root>