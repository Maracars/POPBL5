<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<s:i18n name="action.user.package">

		<div class="jumbotron center-block">
			<s:form action="loginSubmit" namespace="/" key="Login Form">
				<s:textfield key="user.username" name="username" />
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

	</s:i18n>

</div>