<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.user.package">
	<div class="dropdown center-block">
		<button class="btn btn-primary dropdown-toggle" type="button"
			data-toggle="dropdown">
			<s:text name="user.createUser" />
			<span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><s:a namespace="/register" action="Passenger">
					<s:text name="global.passenger" />
				</s:a></li>
			<li><s:a namespace="/register" action="Controller">
					<s:text name="global.controller" />
				</s:a></li>
			<li><s:a namespace="/register" action="Mantainance">
					<s:text name="global.mantainance" />
				</s:a></li>
			<li><s:a namespace="/register" action="Admin">
					<s:text name="global.admin" />
				</s:a></li>
			<li><s:a namespace="/register" action="Airline">
					<s:text name="global.airline" />
				</s:a></li>
		</ul>
	</div>
</s:i18n>