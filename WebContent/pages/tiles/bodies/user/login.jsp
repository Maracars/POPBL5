<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="container-fluid">

	<div class="jumbotron center-block">
		<s:form action="" namespace="">
			<s:textfield name="Username" key="username" />
			<s:textfield name="Password" key="password" />			
			<div class="errors">
				<s:actionerror />
			</div>

		</s:form>


		<div class="well center-block">
			<span>Not registeed yet <s:a action="register">REGISTER!</s:a></span>
		</div>
	</div>


</div>