$(document).ready(
		function() {			
			$("#frmLogro").submit(seleccionaDisponibles);
		});



function seleccionaDisponibles() {
	//alert("En seleccion adisponibles");
	var curOption;		
	curOption = document.getElementById("slcIdElementosDisponibles");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}
	curOption = document.getElementById("slcIdElementosSeleccionados");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}	
	$("#slcIdElementosSeleccionados option").attr("selected", "selected");	
}