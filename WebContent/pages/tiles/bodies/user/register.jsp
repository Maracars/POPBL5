<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<div class="jumbotron">
		<s:form action="registerSubmit" namespace="/">
			<s:textfield key="Username" name="username" />
			<s:textfield key="Password" name="password" />
			<s:textfield key="Repeat the password" name="repeatPassword" />
			<sj:datepicker name="Birthdate" key="birthdate"
				displayFormat="dd-mm-yy" changeYear="true" changeMonth="true" showAnim="slideDown" cssClass="bootstrap"
				label="Birthdate (dd-mm-yyyy)" />
			<s:submit />
		</s:form>
		<div class="errors">
			<s:actionerror />
		</div>
	</div>

</div>