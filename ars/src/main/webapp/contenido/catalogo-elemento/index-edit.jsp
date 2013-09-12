<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Elemento</title>
<jsp:text>
	<![CDATA[
	<script src="${pageContext.request.contextPath}/scripts/catalogo-elemento-editar.js" type="text/javascript"></script>
	 			
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar"
		value="/catalogo-elemento.action?idExpoSel=%{exposicion.idExposicion}"
		includeContext="true" />
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-elemento/%{idElemento}.action?idExpoSel=%{exposicion.idExposicion}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmElemento"
		style="border: 0px" enctype="multipart/form-data">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<center>
			<h1>Editar Elemento</h1>
			<table>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="Nombre" name="model.nombre"
							maxlength="60" size="90" /></td>
				</tr>
				<tr>
					<td><label>Descripción:</label></td>
					<td><s:textarea cols="90" rows="5" id="Descripcion"
							name="model.descripcion" /></td>
				</tr>
				<tr>
					<td>Sonido:</td>
					<td><s:file id="sonido" name="archivoSonido" /></td>
				</tr>
				<tr>
					<td>Imagen:</td>
					<td><s:file id="imagen" name="archivoImagen" /></td>
				</tr>
			</table>
			<sj:submit id="btnAceptar" value="Aceptar" button="true"
				buttonIcon="ui-icon-star" />
			<sj:a id="btnCancelar" button="true" buttonIcon="ui-icon-bullet"
				href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</center>
	</s:form>

	<!-- 
	<center>
		<h3>Recursos</h3>
		<table id="tblElemento">
			<thead>
				<tr>
					<th>Nombre Recurso</th>
					<th>Descripción</th>
					<th>Archivo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="recursosTemporales">
					<tr>
						<td width="150">${nombre}</td>
						<td width="200">${descripcion}</td>
						<td width="150">${nombreArchivoFisico}</td>
						<td width="60"><input type="button"
							onclick="cambiaEditar('${nombre}','${descripcion}','${idTemporal}','${idRecurso}');"
							value="Editar" /> <input type="button"
							onclick="cambiaEliminar('${nombre}','${descripcion}','${idTemporal}','${idRecurso}');"
							value="Eliminar" /></td>
					</tr>
				</s:iterator>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4" style="text-align: center;"><input id="btnNew"
						type="button" onclick="cambiaNuevo();" value="Nuevo Recurso"
						style="width: 150px;" /> <sj:dialog id="mydialog" title="Recurso"
							autoOpen="false" modal="true" width="600">
							<s:form
								action="%{#request.contextPath}/catalogo-recurso?idElementoSel=%{idElemento}"
								method="post" theme="simple" acceptcharset="UTF-8"
								id="frmRecurso" enctype="multipart/form-data"
								style="border: 0px">
								<div id="campos">
									<table>
										<tr>
											<td><label>Nombre:</label></td>
											<td><s:textfield id="nombreRecurso" name="model.nombre"
													maxlength="60" /></td>
										</tr>
										<tr>
											<td colspan="2"><label>Descripción:</label></td>
										</tr>
										<tr>
											<td colspan="2"><s:textarea cols="40" rows="4"
													id="descripcionRecurso" name="model.descripcion" /></td>
										</tr>
										<tr>
											<td><label>Archivo:</label></td>
											<td><s:file id="archivo" name="archivoEvidencia" /> <s:hidden
													id="idtemp" name="model.idTemporal" /> <s:hidden
													id="accion" name="accion" /> <s:hidden id="idRecurso"
													name="model.idRecurso" /></td>
										</tr>
									</table>
								</div>
								<div id="rec">
									<label>Desea eliminar el recurso:</label><br />
									<s:textfield id="nombreRec" maxlength="60" disabled="true" />
								</div>

								<sj:submit id="btnAceptar2" value="Aceptar" button="true"
									buttonIcon="ui-icon-star" cssStyle="width: 200px;" />
								<sj:a id="btnCancelar2" button="true"
									buttonIcon="ui-icon-bullet" href="#"
									onclick="location.href='%{urlCancelar}'"
									cssStyle="width: 200px;">Cancelar</sj:a>

							</s:form>
						</sj:dialog></td>
				</tr>
			</tfoot>
		</table>
	</center>
 -->

</body>
	</html>
</jsp:root>