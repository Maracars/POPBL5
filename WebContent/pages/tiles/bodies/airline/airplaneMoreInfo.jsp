<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="container-fluid">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron">
		<h2><s:property value="plane.model.name"/></h2>
		<h3><s:property value="plane.model.planeMaker.name"/></h3>
		<p>This plane is property of <s:property value="plane.airline.name"/>
		<hr>
		<em>Year: <s:property value="plane.fabricationDate"/></em>

	</div>
</div>