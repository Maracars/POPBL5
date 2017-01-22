<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.airline.package">
	<div class="container-fluid">
		<div class="jumbotron" style="overflow-x: scroll">
			<table id="planestable"
				class="table table-striped table-bordered table-hover table-responsive"
				style="width: 100%;">
				<thead>
					<tr>
						<th><s:text name="airline.serial" /></th>
						<th><s:text name="airline.technicalStatus" /></th>
						<th><s:text name="airline.positionStatus" /></th>
					</tr>
				</thead>
			</table>
			<s:form action="createPlane" namespace="/airline"
				enctype="multipart/form-data" cssClass="form-horizontal">
				<s:submit key="airline.createPlane"
					cssClass="btn btn-primary"></s:submit>

			</s:form>
		</div>

	</div>

</s:i18n>