<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.airline.package">
	<div class="container-fluid" id="airplaneInfo">
		<s:actionerror />
		<s:actionmessage />
		<div class="jumbotron center-block">
			<h1>
				<s:text name="airline.welcome"></s:text>
				<s:property value="#session.user.username" />
				!
			</h1>
			<h2><s:property value="#session.user.username"/> <s:text name="global.airline"></s:text></h2>
			<p><s:text name="airline.contact"/>: <s:property value="#session.user.email"/></p>

		</div>
	</div>

	<div class="container-fluid" id="homeGraphs">
		<s:actionerror />
		<s:actionmessage />
		<div id="pieChart"></div>

		<div id="lineChart"></div>
	</div>
</s:i18n>