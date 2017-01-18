<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>




<s:i18n name="action.user.package">

	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header"></section>
		<section>

			<s:form action="editSubmit" namespace="/" key="global.editUser"
				enctype="multipart/form-data" cssClass="form-vertical">
				<s:hidden key="type" />
				<s:hidden key="username" />
				<div class="errors">
					<s:actionerror />
				</div>
				<h3>
					<strong><s:text name="global.userType" />: </strong>
					<s:if test="%{user != null}">
						<s:property value="%{getText('global.' + type)}" />
					</s:if>
				</h3>
				<div class="well col-lg-4">
					<h4>
						<s:text name="user.accountInfo" />
					</h4>
					<s:textfield id="focused" labelSeparator=":" key="user.username"
						name="user.username" />
					<s:textfield labelSeparator=":" key="user.email" type="email"
						name="user.email" />
				</div>
				<div class="well col-lg-4">
					<s:if test='%{!type.equals("airline")}'>
						<h4>
							<s:text name="user.personalInfo" />
						</h4>
						<s:textfield labelSeparator=":" key="user.firstName"
							name="user.name" />
						<s:textfield labelSeparator=":" key="user.secondName"
							name="user.secondName" />
						<s:text var="dateFormat" name="global.dateFormat" />
						<s:text var="displayDateFormat" name="global.displayDateFormat"></s:text>
						<sj:datepicker labelSeparator=":" name="birthdate"
							parentTheme="bootstrap" cssClass="form-control" showOn="focus"
							inputAppendIcon="calendar" displayFormat="%{displayDateFormat}"
							changeYear="true" changeMonth="true" showAnim="slideDown"
							key="user.birthdate" tooltip="(%{dateFormat})" yearRange="-80:+0" />
					</s:if>
					<s:else>
						<h4>
							<s:text name="user.companyInfo" />
						</h4>
						<s:textfield labelSeparator=":" key="user.companyName"
							name="user.name" />
					</s:else>
				</div>
				<div class="well col-lg-4">
					<h4>
						<s:text name="user.address" />
					</h4>
					<s:textfield labelSeparator=":" key="user.country"
						name="user.address.country" />
					<s:textfield labelSeparator=":" key="user.region"
						name="user.address.region" />
					<s:textfield labelSeparator=":" key="user.city"
						name="user.address.city" />
					<s:textfield labelSeparator=":" key="user.streetAndNo"
						name="user.address.streetAndNumber" />
					<s:textfield labelSeparator=":" key="user.postCode"
						name="user.address.postCode" />
					<s:submit key="global.submit"
						cssClass="btn btn-primary  col-sm-offset-3" />

				</div>
			</s:form>

		</section>

	</div>
</s:i18n>
