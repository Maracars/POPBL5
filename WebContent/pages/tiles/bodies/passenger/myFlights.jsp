<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script src="<s:url value="/js/usernameCheck.js"/>"></script>

<s:i18n name="action.passenger.package">
	<div class="container-fluid" style="overflow-x: scroll">

		<s:form action="unbookFlightSubmit" namespace="/passenger"
			key="global.myFlights" enctype="multipart/form-data"
			cssClass="form-horizontal">
			<div class="errors">
				<s:actionerror />
				<s:actionmessage />
			</div>
			<div class="well">
				<h4>
					<s:text name="passenger.flightInformation" />
				</h4>
				<s:select id="originSelect" list="listOriginAirport" listKey="id" listTitle="passenger.selectOrigin" listValue="name"
						label="%{getText('passenger.selectOrigin')}" labelSeparator=":"></s:select>
						
				<s:select id="destinationSelect" list="listDestinationAirport" listKey="id" listTitle="passenger.selectDestination" listValue="name"
						label="%{getText('passenger.selectDestination')}" labelSeparator=":" name="airport.id"></s:select>
			</div>
			<table id="flightsTable"
				class="table table-striped table-bordered table-hover table-responsive"
				style="width: 100%;">
				<thead>
					<tr>
						<th><s:text name="passenger.source" /></th>
						<th><s:text name="passenger.destination" /></th>
						<th><s:text name="passenger.departureDate" /></th>
						<th><s:text name="passenger.price"/></th>
						<th><s:text name="passenger.planeInfo"/></th>
						<th><s:text name="passenger.unbook"/></th>
					</tr>
				</thead>
			</table>
			</s:form>
	</div>
</s:i18n>