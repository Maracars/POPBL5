<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:i18n name="action.user.package">
	<div class="container-fluid">
		<h2>
			<s:text name="global.userProfile"></s:text>
		</h2>
		<div class="jumbotron">
			<h3>
				<strong><s:text name="global.userType" />: </strong>
				<s:if test="%{user != null}">
				<s:property value="%{getText('global.' + type)}" />
				</s:if>
			</h3>
			<s:actionerror />
			<s:actionmessage />
			<div class="row">
				<div class="well col-md-6 m-1">
					<h2>
						<strong><s:text name="user.username" />: </strong>
						<s:property value="username" />
					</h2>
					<h3>
						<strong><s:text name="user.email" />: </strong>
						<s:property value="user.email" />
					</h3>
					<s:if
						test="%{user instanceof domain.model.users.Passenger || user instanceof domain.model.users.Admin || user instanceof domain.model.users.Mantainance || user instanceof domain.model.users.Controller}">
						<strong><s:text name="user.name" />: </strong>
						<s:property value="user.name" />
						<br>
						<strong><s:text name="user.secondName" />: </strong>
						<s:property value="user.secondName" />
						<br>
						<strong><s:text name="user.birthdate" />: </strong>
						<s:property value="birthdate" />

					</s:if>
					<s:else>
						<strong><s:text name="user.companyName" />: </strong>
						<s:property value="user.name" />
						<br>
					</s:else>


				</div>
				<div class="well col-md-6 ">
					<h2>
						<s:text name="user.address" />
					</h2>
					<strong><s:text name="user.country" />: </strong>
					<s:property value="user.address.country" />
					<br> <strong><s:text name="user.region" />: </strong>
					<s:property value="user.address.region" />
					<br> <strong><s:text name="user.city" />: </strong>
					<s:property value="user.address.city" />
					<br> <strong><s:text name="user.postCode" />: </strong>
					<s:property value="user.address.postCode" />
					<br> <strong><s:text name="user.streetAndNo" />: </strong>
					<s:property value="user.address.streetAndNumber" />
					<br>
				</div>
			</div>
		</div>



	</div>
</s:i18n>