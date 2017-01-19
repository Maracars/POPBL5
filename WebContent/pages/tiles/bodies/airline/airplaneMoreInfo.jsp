<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.airline.package">
<div class="container-fluid">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron">
		<s:hidden name="plane.serial" id="planeSerial"></s:hidden>
		<h2><s:text name="airline.planeModel"/>: <s:property value="plane.model.name"/></h2>
		<h3><s:text name="airline.planeMaker"/>: <s:property value="plane.model.planeMaker.name"/></h3>
		<p><s:text name="airline.planeProperty"/> <s:property value="plane.airline.name"/>
		<hr>
		<em><s:text name="airline.fabricationDate"/>: <s:property value="plane.fabricationDate"/></em>

	</div>
	<div id="barChart">
	</div>
</div>
</s:i18n>