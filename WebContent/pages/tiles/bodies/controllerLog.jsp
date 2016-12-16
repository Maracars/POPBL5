<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="container-fluid">
	<h1>
		
		<s:i18n name="resources.log">
			<s:text name="log.consoleLog" />
		</s:i18n>
	</h1>
	<div class="well">
		<s:a action="launchSimulator" namespace="/" class="btn btn-primary">
			<s:text name="global.startSimulator" />
		</s:a>
	</div>
	<s:actionmessage />
	<ol class="console">

	</ol>
</div>
