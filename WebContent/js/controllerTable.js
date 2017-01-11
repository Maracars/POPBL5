var table;

$(document).ready(function(){
	loadLanesTable();
})

function loadLanesTable(numLi, totalNumLi){

	$('#laneTable').css('display', 'block');
	$('#terminalTable').css('display', 'none');
	$('#termGateNavbar').on('click', "li", function(){
		$('#termGateNavbar li').each(function(index){
			if($(this).attr('class') == 'active'){
				$(this).removeClass();
			}
		});
		$(this).addClass("active");
	})

	if($.fn.dataTable.isDataTable('#lanestable')){
		table = $('#lanestable').DataTable();
	}else{

		table = $('#lanestable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : {
				"url" : "laneListJSON",
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
				"data" : "name"
			}, {
				"data" : "state",
				"createdCell" : function(td, cellData, rowData, row, col) {
					if (cellData == "Occupied") {
						$(td).addClass("danger");
					} else {
						$(td).addClass("success");
					}
				}
			} ]
		});
	}
}

function loadTerminalsTable(){

	$('#laneTable').css('display', 'none');
	$("#terminalTable").css('display', 'block');
	$('#termGateNavbar').on('click', 'li', function(){
		$('#termGateNavbar li').each(function(index){
			if($(this).attr('class') == 'active'){
				$(this).removeClass();
			}
		});
		$(this).addClass("active");
	});

	if($.fn.dataTable.isDataTable('#terminalstable')){
		table = $('#terminalstable').DataTable();
	}else{

		table = $('#terminalstable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : {
				"url" : "terminalListJSON",
				"type" : "POST"
			},
			"columnDefs" : [ {
				"targets" : 0,
				"orderable" : false,
			}, {
				"width" : "120px",
				"targets" : 0
			} ],
			"columns" : [ {
				"data" : "terminalName"
			}, {
				"data" : "gateName"
			}, {
				"data" : "gateState",
				"createdCell" : function(td, cellData, rowData, row, col) {
					if (cellData == "Occupied") {
						$(td).addClass("danger");
					} else {
						$(td).addClass("success");
					}
				}
			} ]
		});
	}
}