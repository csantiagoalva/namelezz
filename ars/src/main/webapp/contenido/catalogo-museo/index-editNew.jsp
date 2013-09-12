<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agregar Museo</title>
<jsp:text>
	<![CDATA[			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/seleccionar-temas.js" >
			</script>
			
		 ]]>
</jsp:text>
</head>
<body>

	<s:url id="urlCancelar" value="/catalogo-museo.action"
		includeContext="true" />
	<s:actionerror id="saeMuseo" theme="jquery" />
	<s:fielderror id="sfeMuseo" theme="jquery" />


	<s:form action="%{#request.contextPath}/catalogo-museo.action"
		method="post" theme="simple" acceptcharset="UTF-8" align="center"
		id="frmMuseo" cssStyle="border: 0px" enctype="multipart/form-data">
		<center>
			<h1>Agregar Museo</h1>
			<table>
				<tr>
					<td style="text-align: center"><label>Seleccione el
							tema o temas del Museo:</label><br /> <s:optiontransferselect
							label="Temas Registrados" name="" id="slcIdTemasDisponibles"
							list="temasRegistrados" listValue="nombre" listKey="idTema"
							doubleName="idTemasSeleccionados"
							doubleId="slcIdTemasSeleccionados" doubleList="temasMuseo"
							doubleListValue="nombre" allowUpDownOnLeft="false"
							doubleListKey="idTema" allowUpDownOnRight="false"
							allowAddAllToLeft="false" allowAddAllToRight="false"
							allowSelectAll="false" /> <br /> <a
						href="${pageContext.request.contextPath}/catalogo-tema/new.action"><input
							type="button" value="Agregar Nuevo Tema" /> </a><br /></td>
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
								<td><label>Calle:</label></td>
								<td><s:textfield id="calle" name="model.calle"
										maxlength="50" /></td>
								<td><label>Número:</label></td>
								<td><s:textfield id="numero" name="model.numero"
										maxlength="50" /></td>
							</tr>
							<tr>
								<td><label>Colonia:</label></td>
								<td><s:textfield id="colonia" name="model.colonia"
										maxlength="50" /></td>
								<td><label>Delegación:</label></td>
								<td><s:textfield id="delegacion" name="model.delegacion"
										maxlength="50" /></td>
							</tr>
							<tr>
								<td><label>Estado:</label></td>
								<td><s:textfield id="estado" name="model.estado"
										maxlength="50" /></td>
								<td><label>CP:</label></td>
								<td><s:textfield id="codigo" name="model.codigo"
										maxlength="50" /></td>
							</tr>
							<tr>
								<td><label>Contacto:</label></td>
								<td colspan="3"><s:textfield name="model.contacto"
										id="contacto" maxlength="50" size="60" /></td>
							</tr>
							<tr>
								<td><label>Actividad:</label></td>
								<td><s:select id="actividad" name="model.edoMuseo"
										list="edo" requiered="true" /></td>
							</tr>
							<tr>
								<td>Imagen:</td>
								<td><s:file id="img" name="archivoImagen" /></td>
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