var giCount = 1;

$(document).ready(function() {
	$('#tblElemento').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"sDom" : '<"H"Tfr>t<"F"ip>',
		"oTableTools" : {
			"aButtons" : [ "copy", "csv", "xls", "pdf", {
				"sExtends" : "collection",
				"sButtonText" : "Save",
				"aButtons" : [ "csv", "xls", "pdf" ]
			} ]
		}
	});
		
}

);


function cambiaNuevo(){
	$("#mydialog").dialog("open");
	$("#campos").show();
	$("#rec").hide();
	document.getElementById('nombreRecurso').value=null;	
	document.getElementById('descripcionRecurso').value=null;
	document.getElementById('idtemp').value=null;
	document.getElementById('accion').value=null;	
}

function cambiaEditar(nombre,descripcion,idtemp,idrecurso){	
	$("#mydialog").dialog("open");
	$("#campos").show();
	$("#rec").hide();
	document.getElementById('nombreRecurso').value=nombre;
	document.getElementById('descripcionRecurso').value=descripcion;
	document.getElementById('idtemp').value=idtemp;
	document.getElementById('accion').value="Editar";
	document.getElementById('idRecurso').value=idrecurso;	
}

function cambiaEliminar(nombre,descripcion,idtemp,idrecurso){
	$("#mydialog").dialog("open");
	$("#campos").hide();
	$("#rec").show();
	document.getElementById('nombreRecurso').value=nombre;
	document.getElementById('nombreRec').value=nombre;
	document.getElementById('descripcionRecurso').value=descripcion;
	document.getElementById('idtemp').value=idtemp;
	document.getElementById('accion').value="Eliminar";
	document.getElementById('idRecurso').value=idrecurso;	
}
