<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<div class="jumbotron center-block">
		<s:form action="loginSubmit" namespace="/" key="Login Form">
			<s:textfield label="Username" name="username" />
			<s:password label="Password" name="password" />			
			<div class="errors">
				<s:actionerror />
			</div>
			<s:submit />
		</s:form>


		<div style="margin-top: 50px;" "class="well center-block">
			<span>Not registeed yet <s:a action="register">REGISTER!</s:a></span>
		</div>
	</div>


</div>