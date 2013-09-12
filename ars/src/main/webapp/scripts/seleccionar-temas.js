$(document).ready(
		function() {			
			$("#frmMuseo").submit(seleccionaDisponibles);
		});



function seleccionaDisponibles() {
	//alert("En seleccion adisponibles");
	var curOption;		
	curOption = document.getElementById("slcIdTemasDisponibles");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}
	curOption = document.getElementById("slcIdTemasSeleccionados");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}	
	$("#slcIdTemasSeleccionados option").attr("selected", "selected");	
}