<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>



<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header"></section>
	<section>
		<s:actionerror />
		<s:actionmessage />
		<div class="jumbotron center-block">
			<h1>Airline</h1>
			<h2>NaranAir Airline</h2>
			<p>Here is the basic information about the airline</p>

		</div>
		<div class="container-fluid" id="homeGraphs">
			<s:actionerror />
			<s:actionmessage />
			<div id="pieChart"></div>

			<div id="lineChart"></div>
		</div>
	</section>

</div>
