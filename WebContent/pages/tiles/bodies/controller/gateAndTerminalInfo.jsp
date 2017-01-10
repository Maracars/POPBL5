<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:i18n name="action.controller.package">

	<div class="container-fluid" id="laneTable">
		<div class="jumbotron">
			<table id="lanestable"
				class="table table-striped table-bordered table-hover table-responsive"
				style="width: 100%;">
				<thead>
					<tr>
						<th><s:text name="controller.laneName" /></th>
						<th><s:text name="controller.laneStatus" /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			var table = $('#lanestable').dataTable({
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

		});
	</script>

	<div class="container-fluid" id="terminalTable">
		<div class="jumbotron">
			<table id="terminalstable"
				class="table table-striped table-bordered table-hover table-responsive"
				style="width: 100%;">
				<thead>
					<tr>
						<th><s:text name="controller.terminalName" /></th>
						<th><s:text name="controller.gateName" /></th>
						<th><s:text name="controller.gateStatus" /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			var table = $('#terminalstable').dataTable({
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

		});
	
		
	</script>

</s:i18n>