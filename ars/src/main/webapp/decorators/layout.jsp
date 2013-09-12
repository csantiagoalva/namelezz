<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags" xmlns:sjc="/struts-jquery-chart-tags"
	xmlns:log="http://jakarta.apache.org/taglibs/log-1.0"
	xmlns:sjg="/struts-jquery-grid-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<sj:head debug="true" jqueryui="true" jquerytheme="dot-luv" locale="es" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_page.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table_jui.css" />

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/Estilos/print.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/Estilos/main.css" />

<jsp:text>
	<![CDATA[ 						
	
			<script src="${pageContext.request.contextPath}/struts/js/plugins/jquery.jqGrid.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/struts/js/plugins/jquery.form.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/dataTables/media/js/jquery.dataTables.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.blockUI.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.feedback.js" type="text/javascript"></script>
			<script src='${pageContext.request.contextPath}/dwr/engine.js' type='text/javascript'></script>
			<script src='${pageContext.request.contextPath}/dwr/util.js' type='text/javascript'></script>
			<script src="${pageContext.request.contextPath}/scripts/cdt-util.js" type="text/javascript"></script>
		]]>
</jsp:text>

<decorator:head />

</head>
<body>
	<div id="pageHeader">
		<div class="banner">
			<label style="font-size: 15px;"></label>
		</div>
	</div>
	<input type="text" style="display: none;" id="hdnRutaContexto"
		value="${pageContext.request.contextPath}" />

	<div id="pageMenu">
		<center>
			<table style="margin-top: 0px;">
				<tr>
					<td><a class="selected"
						href="${pageContext.request.contextPath}/catalogo-museo.action">
							<h3 style="width: 300px">Museos</h3>
					</a></td>
					<td><a class="selected"
						href="${pageContext.request.contextPath}/catalogo-tema.action">
							<h3 style="width: 300px">Temas</h3>
					</a></td>
					<td><a class="selected"
						href="${pageContext.request.contextPath}/catalogo-subtema.action"><h3
								style="width: 300px">Subtemas</h3></a></td>
					<td><a class="selected"
						href="${pageContext.request.contextPath}/logout.action"><h3
								style="width: 300px">Cerrar Sesión</h3></a></td>
				</tr>
			</table>
		</center>
	</div>
	<div id="pageContent">

		<decorator:body />
	</div>
	<!-- <div id="pageMain">		
	</div> -->
	<div id="pageFooter">
		<h2>Escuela Superior de Cómputo.</h2>
		<p>Av. Juan de Dios Bátiz s/n esquina Miguel Othón de Mendizabal.
			Unidad Profesional Adolfo López Mateos. Col. Lindavista C.P. 07738,
			México, D. F.</p>
		E-mail: <a href="mailto:csantiagoalva@gmail.com">csantiagoalva@gmail.com</a>

	</div>
</body>
	</html>
</jsp:root>