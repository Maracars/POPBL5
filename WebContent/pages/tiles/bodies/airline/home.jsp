<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="container-fluid" id="airplaneInfo">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron center-block">
		<h1>Airline</h1>
		<h2>NaranAir Airline</h2>
		<p>Here is the basic information about the airline
		</p>

	</div>
</div>

<div class="container-fluid" id="homeGraphs">
	<s:actionerror />
	<s:actionmessage />
	<div id="pieChart">

	</div>
	
		<div  id="barChart">

	</div>
</div>