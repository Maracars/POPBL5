<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.user.package">
	<div class="container-fluid">

		<s:form action="register/ControllerSubmit"
			key="global.registerController" enctype="multipart/form-data"
			cssClass="form-horizontal">
			<div class="errors">
				<s:actionerror />
			</div>
			<div class="well">
				<h4>
					<s:text name="user.accountInfo" />
				</h4>
				<s:textfield id="focused" labelSeparator=":" key="user.username"
					name="user.username" class ="username"/>
				<s:password labelSeparator=":" key="user.password"
					name="user.password" />
				<s:password labelSeparator=":" key="user.repeatPassword"
					name="repeatPassword" />
				<s:textfield labelSeparator=":" key="user.email" type="email" name="user.email" />
			</div>
			<div class="well">
				<h4>
					<s:text name="user.personalInfo" />
				</h4>
				<s:textfield labelSeparator=":" key="user.firstName" name="name" />
				<s:textfield labelSeparator=":" key="user.secondName"
					name="secondName" />
				<s:text var="dateFormat" name="global.dateFormat" />
				<s:text var="displayDateFormat" name="global.displayDateFormat"></s:text>
				<sj:datepicker labelSeparator=":" name="birthdate"
					parentTheme="bootstrap" cssClass="form-control" showOn="focus"
					inputAppendIcon="calendar" displayFormat="%{displayDateFormat}"
					changeYear="true" changeMonth="true" showAnim="slideDown"
					key="user.birthdate" tooltip="(%{dateFormat})" yearRange="-80:+0" />
			</div>
			<div class="well">
				<h4>
					<s:text name="user.address" />
				</h4>
				<s:textfield labelSeparator=":" key="user.country"
					name="address.country" />
				<s:textfield labelSeparator=":" key="user.region"
					name="address.region" />
				<s:textfield labelSeparator=":" key="user.city" name="address.city" />
				<s:textfield labelSeparator=":" key="user.streetAndNo"
					name="address.streetAndNumber" />
				<s:textfield labelSeparator=":" key="user.postCode"
					name="address.postCode" />
				<s:submit key="global.submit"
					cssClass="btn btn-primary  col-sm-offset-3" />

			</div>


		</s:form>

	</div>
</s:i18n>