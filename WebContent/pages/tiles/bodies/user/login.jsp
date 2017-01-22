<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<s:i18n name="action.user.package">
	<div class="center-block row">
		<div class="jumbotron col-md-6 col-md-offset-3">
			<s:form action="loginSubmit" namespace="/" key="Login Form">
				<s:textfield key="user.username" name="username" id="focused"/>
				<s:password key="user.password"
					name="password" />
				<div class="errors">
					<s:actionerror />
				</div>
				<s:submit key="global.submit"/>
			</s:form>


			<div style="margin-top: 50px;" class="well center-block">
				<span><s:text name="user.notRegisteredYet" />, <s:a
						action="Passenger" namespace="register">
						<s:text name="global.register" />!</s:a></span>
			</div>
		</div>
		</div>

	</s:i18n>

</div>