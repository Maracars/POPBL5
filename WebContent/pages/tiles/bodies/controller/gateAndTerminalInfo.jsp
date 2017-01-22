<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:i18n name="action.controller.package">

	<div class="container-fluid" >
		<ul class="nav nav-pills nav-justified" id="termGateNavbar">
			<li class="active"><a href="javascript:loadLanesTable(0,1)">Lanes</a></li>
			<li><a href="javascript:loadTerminalsTable(1,1)">Terminal
					and Gates</a></li>
		</ul>
	</div>
	<div class="container-fluid" id="laneTable">
		<div class="jumbotron" style="overflow-x: scroll">
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

	<div class="container-fluid" id="terminalTable">
		<div class="jumbotron" style="overflow-x: scroll">
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

</s:i18n>