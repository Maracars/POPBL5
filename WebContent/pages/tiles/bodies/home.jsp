<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron">

		<h2>
			<s:text name="global.welcomeMessage" />
		</h2>

		<hr>
		<em> designed by MARACARS</em>

	</div>
	<div class="jumbotron">
			<table id="flightstable"
				class="table table-striped table-bordered table-hover table-responsive"
				style="width: 100%;">
				<thead>
					<tr>
						<th><s:text name="user.time" /></th>
						<th><s:text name="user.destiny" /></th>
						<th><s:text name="user.flightInfo" /></th>
						<th><s:text name="user.positionStatus" /></th>
					</tr>
				</thead>
			</table>
		</div>
		<script>
		$(document).ready(function() {
			var table = $('#flightstable').dataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax" : {
					"url" : "nextArrivingPlanes",
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
					"data" : "date"
				}, {
					"data" : "origin"
				}, {
					"data" : "flightInfo"
				}, {
					"data" : "positionStatus"
				} ]
			});

		});
	</script>
</div>