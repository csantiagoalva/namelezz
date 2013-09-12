<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:text>
	<![CDATA[			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/seleccionar-subtemas.js" >
			</script>
			
		 ]]>
</jsp:text>
<title>Modificar Exposicion</title>
</head>
<body>

	<s:url id="urlCancelar" value="/catalogo-exposicion.action"
		includeContext="true" />
	<s:actionerror id="saeExpo" theme="jquery" />
	<s:fielderror id="sfeExpo" theme="jquery" />
	<s:form id="frmExpo"
		action="%{#request.contextPath}/catalogo-exposicion/%{idExposicion}.action"
		method="post" theme="simple" acceptcharset="UTF-8"
		cssStyle="border: 0px" enctype="multipart/form-data">
		<center>
			<h1>Modificar Exposicion</h1>
			<s:hidden id="hdnMethod" name="_method" value="put" />


			<table>
				<tr>
					<td><label>Seleccione el subtema o <br />subtemas de
							la Exposición:
					</label><br /> <s:optiontransferselect label="Subtemas Registrados" name=""
							id="slcIdSubtemasDisponibles" list="subtemasRegistrados"
							listValue="nombre" listKey="idSubtema"
							doubleName="idSubtemasSeleccionados"
							doubleId="slcIdSubtemasSeleccionados" doubleList="model.subtemas"
							doubleListValue="nombre" allowUpDownOnLeft="false"
							doubleListKey="idSubtema" allowUpDownOnRight="false"
							allowAddAllToLeft="false" allowAddAllToRight="false"
							allowSelectAll="false" /> <br /> <a
						href="${pageContext.request.contextPath}/catalogo-subtema/new.action"><input
							type="button" value="Agregar Nuevo Subtema" /> </a></td>

					<td>
						<table>
							<tr>
								<td><label>Nombre:</label></td>
								<td colspan="3"><s:textfield id="Nombre"
										name="model.nombre" maxlength="50" size="60" /></td>
							</tr>
							<tr>
								<td><label>Descripción:</label></td>
								<td colspan="3"><s:textarea rows="3" cols="70"
										id="Descripcion" name="model.descripcion" maxlength="200" /></td>
							</tr>
							<tr>
								<td><label>Actividad:</label></td>
								<td><s:select id="actividad" name="model.edoExposicion"
										list="edo" requiered="true" /></td>
								<td><label>Tipo:</label></td>
								<td><s:select id="tipo" name="model.tipo" list="type"
										requiered="true" onchange="tipoEx();" /></td>
							</tr>
						</table>
						<div id="fechas">
							<table>
								<tr>
									<td><label>Fecha Inicio:</label></td>
									<td><sj:datepicker name="model.fhInicio"
											displayFormat="yy-mm-dd" id="dtpFechaInicio"
											cssClass="campoFechas" /></td>
									<td><label>Fecha Fin:</label></td>
									<td><sj:datepicker name="model.fhFin"
											displayFormat="yy-mm-dd" id="dtpFechaFin"
											cssClass="campoFechas" /></td>
								</tr>
							</table>
						</div>
						<table>
							<tr>
								<td>Archivo .dat:</td>
								<td><s:file id="dat" name="archivoDat" /></td>
								<td>Archivo .xml:</td>
								<td><s:file id="xml" name="archivoXml" /></td>
							</tr>
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