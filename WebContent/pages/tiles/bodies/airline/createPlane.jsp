<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:i18n name="action.airline.package">

	<div class="container-fluid">

		<s:form action="createPlaneSubmit" namespace="/airline"
			key="global.createAirplane" enctype="multipart/form-data"
			cssClass="form-horizontal">
			<div class="errors">
				<s:actionerror />
			</div>
			<h3>
				<strong><s:text name="global.createAirplane" />: </strong>
			</h3>
			<div class="well">
				<h4>
					<s:text name="airline.planeInfo" />
				</h4>
				<s:textfield id="focused" labelSeparator=":" key="airline.serial"
					name="plane.serial" />
			</div>
			<div class="well">
				<h4>
					<s:text name="airline.planeModelInfo" />
				</h4>
				<s:if test="%{!getListPlaneModel().isEmpty()}">
					<s:select list="listPlaneModel" listKey="id" listTitle="airline.selectPlaneModel" listValue="name"
						label="%{getText('airline.selectPlaneModel')}" labelSeparator=":" name="planeModel.id"></s:select>
				</s:if>
				<s:else>
					<s:textfield labelSeparator=":" key="airline.planeModelName"
						name="planeModel.name" />
				</s:else>
			</div>
			<div class="well">
				<h4>
					<s:text name="airline.planeMakerInfo"></s:text>
				</h4>
				<s:if test="%{!getListPlaneMaker().isEmpty()}">
					<s:select list="listPlaneMaker" listKey="id" listValue="name"
						label="%{getText('airline.selectPlaneMaker')}" labelSeparator=":" name="planeMaker.id"></s:select>
				</s:if>
				<s:else>
					<s:textfield labelSeparator=":" key="airline.planeMakerName"
						name="planeMaker.name" />
				</s:else>
				<s:submit key="global.submit"
					cssClass="btn btn-primary  col-sm-offset-3" />
			</div>
		</s:form>

	</div>

</s:i18n>