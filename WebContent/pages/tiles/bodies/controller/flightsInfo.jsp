<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:i18n name="action.controller.package">
	<div class="container-fluid">
		<table id="flightstable"
			class="table table-striped table-bordered table-hover table-responsive"
			style="width: 100%;">
			<thead>
				<tr>
					<th><s:text name="controller.airplane" /></th>
					<th><s:text name="controller.origin" /></th>
					<th><s:text name="controller.destination" /></th>
					<th><s:text name="controller.terminal" /></th>
					<th><s:text name="controller.gate" /></th>
				</tr>
			</thead>
		</table>
 	</div>
<script>
		$(document).ready(function() {
			$('#flightstable').dataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					"url" : "flightListJSON",
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
					"data" : "airplane"
				}, {
					"data" : "origin"
				}, {
					"data" : "destination"
				}, {
					"data" : "terminal"
				}, {
					"data" : "gate"
				} ]
			});
		});
	</script>
</s:i18n>