<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agregar Logro</title>
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
		action="%{#request.contextPath}/catalogo-logro.action?idMuseoSel=%{idMuseoSel}"
		method="post" theme="simple" acceptcharset="UTF-8" align="center"
		id="frmLogro" cssStyle="border: 0px" enctype="multipart/form-data">
		<center>
			<h1>Agregar Logro</h1>
			<table>
				<tr>
					<td style="text-align: center"><label>Seleccione el
							elemento o elementos del Logro:</label><br /> <s:optiontransferselect
							label="Elementos Registrados" name=""
							id="slcIdElementosDisponibles" list="elementosRegistrados"
							listValue="nombre" listKey="idElemento"
							doubleName="idElementosSeleccionados"
							doubleId="slcIdElementosSeleccionados"
							doubleList="elementosLogro" doubleListValue="nombre"
							allowUpDownOnLeft="false" doubleListKey="idElemento"
							allowUpDownOnRight="false" allowAddAllToLeft="false"
							allowAddAllToRight="false" allowSelectAll="false" /><br /></td>
					<td><table>
							<tr>
								<td><label>Nombre:</label></td>
								<td colspan="3"><s:textfield id="Nombre"
										name="model.nombre" maxlength="100" size="60" /></td>
							</tr>
							<tr>
								<td><label>Descripción:</label></td>

								<td colspan="3"><s:textarea rows="4" cols="60"
										id="Descripcion" name="model.descripcion" maxlength="200" /></td>
							</tr>
							<tr>
								<td>Modelo:</td>
								<td><s:file id="modelo" name="archivo" /></td>
							</tr>
							<tr>
								<td>Textura:</td>
								<td><s:file id="textura" name="textura" /></td>
							</tr>


						</table></td>
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