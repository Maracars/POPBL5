<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:i18n name="action.user.package">

	<div class="container-fluid">
		<s:actionerror />
		<s:actionmessage />
		<div class="alert alert-dismissable">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
			<h2>
				<s:text name="global.welcomeMessage" />
			</h2>

			<hr>
			<em> designed by MARACARS</em>

		</div>
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#departuring">Departuring
					flights</a></li>
			<li><a data-toggle="tab" href="#arriving">Arriving flights</a></li>
		</ul>

		<div class="tab-content">
			<div id="departuring" class="tab-pane active">
				<table id="flightstableDeparture"
					class="table table-striped table-bordered table-hover table-responsive"
					style="width: 100%;">
					<thead>
						<tr>
							<th><s:text name="user.departureDate" /></th>
							<th><s:text name="user.destination" /></th>
							<th><s:text name="user.flightInfo" /></th>
							<th><s:text name="user.positionStatus" /></th>
						</tr>
					</thead>
				</table>
			</div>
			<div id="arriving" class="tab-pane fade">
				<table id="flightstableArrival"
					class="table table-striped table-bordered table-hover table-responsive"
					style="width: 100%;">
					<thead>
						<tr>
							<th><s:text name="user.arrivalDate" /></th>
							<th><s:text name="user.origin" /></th>
							<th><s:text name="user.flightInfo" /></th>
							<th><s:text name="user.positionStatus" /></th>
						</tr>
					</thead>
				</table>
			</div>

		</div>


		<script>
			$(document).ready(function() {
				var table = $('#flightstableDeparture').dataTable({
					"processing" : true,
					"serverSide" : true,
					"ajax" : {
						"url" : "nextDeparturingPlanes",
						"type" : "POST"
					},
					"columnDefs" : [ {
						"targets" : 0,
						"orderable" : false
					}, {
						"width" : "180px",
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

				var table = $('#flightstableArrival').dataTable({
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
						"width" : "180px",
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
</s:i18n>