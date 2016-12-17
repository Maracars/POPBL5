<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">
	<div class="jumbotron">
		<div style="position: absolute;">
			<s:actionerror/>
			<s:actionmessage/>
			<h1>500</h1>
			<h2>T.T <s:text name="global.internalError"/> T.T</h2>
			<p><s:text name="global.webpageCrahsed"/></p>
		</div>
		<img class="img-responsive center-block" alt="Error 500 Image" src="rsc/img/500.gif">
	</div>
</div>