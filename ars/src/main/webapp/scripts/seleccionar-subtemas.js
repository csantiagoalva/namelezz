$(document).ready(
		function() {			
			$("#frmExpo").submit(seleccionaDisponibles);
			if($("#tipo").val()=="Permanente"){
				$("#fechas").hide();
			}else{
				$("#fechas").show();
			}
		});



function seleccionaDisponibles() {
	//alert("En seleccion adisponibles");
	var curOption;		
	curOption = document.getElementById("slcIdSubtemasDisponibles");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}
	curOption = document.getElementById("slcIdSubtemasSeleccionados");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}	
	$("#slcIdSubtemasSeleccionados option").attr("selected", "selected");	
}

function tipoEx(){
	
	if($("#tipo").val()=="Permanente"){
		$("#fechas").hide();
	}else{
		$("#fechas").show();
	}
	
}