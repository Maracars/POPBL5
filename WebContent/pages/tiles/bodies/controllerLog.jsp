<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:i18n name="resources.log">
	<div class="container-fluid">
		<div>

			<div class="container">

				<h1>
					<s:text name="log.consoleLog" />
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
		</div>
	</div>
</s:i18n>