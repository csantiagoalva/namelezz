$(document).ready( function () {
	$('#tblExposicion').dataTable( {
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<"H"Tfr>t<"F"ip>',
		"oTableTools": {
			"aButtons": [
				"copy", "csv", "xls", "pdf",
				{
					"sExtends":    "collection",
					"sButtonText": "Save",
					"aButtons":    [ "csv", "xls", "pdf" ]
				}
			]
		}
	} );
} );