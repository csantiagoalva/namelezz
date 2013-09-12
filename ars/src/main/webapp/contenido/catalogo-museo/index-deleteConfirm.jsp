<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Museo</title>
</head>
<body>

	<s:url id="urlCancelar" value="/catalogo-museo" includeContext="true" />
	<s:actionerror id="saeMuseo" theme="jquery" />
	<s:fielderror id="sfeMuseo" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-museo/%{idMuseo}"
		method="post" theme="simple" acceptcharset="UTF-8"
		cssStyle="border: 0px">
		<center>
			<h1>Eliminar Museo</h1>
			<s:hidden id="hdnMethod" name="_method" value="delete" />
			<table>
				<tr>
					<td><table id="tblMuseo">
							<thead>
								<tr>
									<th>Temas</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="model.temas">
									<tr>
										<td>${nombre}</td>
									</tr>
								</s:iterator>
							</tbody>
						</table></td>
					<td><table>
							<tr>
								<td><label>Nombre:</label></td>
								<td colspan="3"><s:textfield id="Nombre"
										name="model.nombre" maxlength="50" size="60" disabled="true" /></td>
							</tr>


							<tr>
								<td><label>Descripción:</label></td>
								<td colspan="3"><s:textarea rows="4" cols="60"
										id="Descripcion" name="model.descripcion" maxlength="200"
										disabled="true" /></td>
							</tr>
							<tr>
								<td><label>Calle:</label></td>
								<td><s:textfield id="calle" name="model.calle"
										maxlength="50" disabled="true" /></td>
								<td><label>Número:</label></td>
								<td><s:textfield id="numero" name="model.numero"
										maxlength="50" disabled="true" /></td>
							</tr>
							<tr>
								<td><label>Colonia:</label></td>
								<td><s:textfield id="colonia" name="model.colonia"
										maxlength="50" disabled="true" /></td>
								<td><label>Delegación:</label></td>
								<td><s:textfield id="delegacion" name="model.delegacion"
										maxlength="50" disabled="true" /></td>
							</tr>
							<tr>
								<td><label>Estado:</label></td>
								<td><s:textfield id="estado" name="model.estado"
										maxlength="50" disabled="true" /></td>
								<td><label>CP:</label></td>
								<td><s:textfield id="codigo" name="model.codigo"
										maxlength="50" disabled="true" /></td>
							</tr>
							<tr>
								<td><label>Contacto:</label></td>
								<td colspan="3"><s:textfield id="contacto"
										name="model.contacto" maxlength="50" size="60" disabled="true" /></td>
							</tr>
							<tr>
								<td><label>Actividad:</label></td>
								<td><s:textfield id="actividad" name="model.edoMuseo"
										maxlength="50" disabled="true" /></td>
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