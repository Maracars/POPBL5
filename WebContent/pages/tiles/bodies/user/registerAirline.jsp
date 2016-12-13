<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:i18n name="action.user.package">
	<div class="container-fluid">

		<s:form action="AirlineSubmit" namespace="/register"
			key="global.registerAirline" enctype="multipart/form-data"
			cssClass="form-horizontal">
			<div class="well">
				<h4>
					<s:text name="user.accountInfo" />
				</h4>
				<s:textfield labelSeparator=":"
					label="<s:text name="user.username"/>" name="user.username" />
				<s:password labelSeparator=":"
					label="<s:text name="user.password"/>" name="user.password" />
				<s:password labelSeparator=":"
					label="<s:text name="user.repeatPassword"/>" name="repeatPassword" />
				<s:textfield labelSeparator=":" label="<s:text name="user.email"/>"
					name="user.email" />
			</div>
			<div class="well">
				<h4>
					<s:text name="user.companyInfo" />
				</h4>
				<s:textfield labelSeparator=":"
					label="<s:text name="user.companyName"/>" name="name" />
			</div>
			<div class="well">
				<h4>
					<s:text name="user.address" />
				</h4>
				<s:textfield labelSeparator=":"
					label="<s:text name="user.country"/>" name="address.country" />
				<s:textfield labelSeparator=":" label="<s:text name="user.region"/>"
					name="address.region" />
				<s:textfield labelSeparator=":" label="<s:text name="user.city"/>"
					name="address.city" />
				<s:textfield labelSeparator=":"
					label="<s:text name="user.streetAndNo"/>"
					name="address.streetAndNumber" />
				<s:textfield labelSeparator=":"
					label="<s:text name="user.postCode"/>" name="address.postCode" />
				<s:submit cssClass="btn btn-primary  col-sm-offset-3" />

			</div>

		</s:form>
		<div class="errors">
			<s:actionerror />
		</div>

	</div>
</s:i18n>