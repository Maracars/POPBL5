<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<div class="jumbotron">
		<s:form action="registerSubmit" namespace="/" key="User registry form">
			<s:textfield label="Username" name="user.username" />
			<s:textfield label="Password" name="user.password" />
			<s:textfield label="Repeat the password" name="repeatPassword" />
			<sj:datepicker name="birthdate"
				displayFormat="dd-mm-yy" changeYear="true" changeMonth="true" showAnim="slideDown" cssClass="bootstrap"
				label="Birthdate (dd-mm-yyyy)" yearRange="-80:+0" />
			<s:submit />
		</s:form>
		<div class="errors">
			<s:actionerror />
		</div>
	</div>

</div>