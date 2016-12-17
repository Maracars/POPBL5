<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">
	<div class="jumbotron">
		<s:actionerror />
		<s:actionmessage />
		<h1>404</h1>
		<h2>
			?¿?¿
			<s:text name="global.notFound" />
			?¿?¿
		</h2>
		<p>
			<s:text name="global.resourceNotFound" />
		</p>
		<img class="img-responsive center-block" alt="Error 404 Image"
			src="rsc/img/404.png">
	</div>
</div>