
		$(document).ready(function() {
			var table = $('#planestable').dataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					"url" : "airplaneListJSON",
					"type" : "POST"
				},
				"columnDefs" : [ {
					"targets" : 0,
					"orderable" : false
				}, {
					"width" : "120px",
					"targets" : 0
				} ],
				"columns" : [ {
					"data" : "serial",
					"render" : function(data, type, full, meta){
						return '<div><a href="airplaneMoreInfo/'+data+'">'+data+'</a></div>';
					}
				}, {
					"data" : "technicalStatus"
				}, {
					"data" : "positionStatus"
				} ]
			});

		});