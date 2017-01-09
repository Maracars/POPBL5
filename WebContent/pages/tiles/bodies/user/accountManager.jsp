<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.user.package">
	<div class="dropdown center-block">
		<button class="btn btn-primary dropdown-toggle" type="button"
			data-toggle="dropdown">
			<s:text name="user.createUser" />
			<span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><s:a namespace="/register" action="Passenger">
					<s:text name="global.passenger" />
				</s:a></li>
			<li><s:a namespace="/register" action="Controller">
					<s:text name="global.controller" />
				</s:a></li>
			<li><s:a namespace="/register" action="Mantainance">
					<s:text name="global.mantainance" />
				</s:a></li>
			<li><s:a namespace="/register" action="Admin">
					<s:text name="global.admin" />
				</s:a></li>
			<li><s:a namespace="/register" action="Airline">
					<s:text name="global.airline" />
				</s:a></li>
		</ul>
	</div>
	<h3>

		<s:text name="global.users" />
	</h3>
	<div class="container-fluid">
		<table id="usertable"
			class="table table-striped table-bordered table-hover table-responsive"
			style="width: 100%;">
			<thead>
				<tr>
					<th></th>
					<th><s:text name="global.userType" /></th>
					<th><s:text name="user.username" /></th>
					<th><s:text name="user.name" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<script>
		$(document)
				.ready(
						function() {

							$('#usertable')
									.dataTable(
											{
												"processing" : true,
												"serverSide" : true,
												"ajax" : {
													"url" : "userListJSON",
													"type" : "POST"
												},
												"columnDefs" : [ {
													"targets" : 0,
													"orderable" : false
												}, {
													"width" : "120px",
													"targets" : 0
												} ],
												"columns" : [
														{
															"data" : "username",
															"render" : function(
																	data, type,
																	full, meta) {
																return '<div><a class="btn btn-primary" href="u/'+data+'"><i class="fa fa-user"></i></a>'
																		+ '<a class="btn btn-warning" href="u/edit/'+data+'"><i class="fa fa-pencil-square-o"></i></a>'
																		+ '<a class="btn btn-danger" href="deleteUser?username='
																		+ data
																		+ '"><i class="fa fa-times"></i></a></div';
															}
														}, {
															"data" : "type"
														}, {
															"data" : "username"
														}, {
															"data" : "name"
														} ]
											});
							
							$.fn.dataTable.ext.errMode = function(settings, helpPage, message) {
								$.notify(`No permission to load data`, {
									 type: 'danger', offset: { x: 50, y: 50 }, animate: { enter: 'animated	 bounceInDown', exit: 'animated lightSpeedOut' } });
							};
							
						});

		
	</script>

</s:i18n>
