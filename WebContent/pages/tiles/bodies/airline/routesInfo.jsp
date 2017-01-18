<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header"></section>
	<section>
		<s:actionerror />
		<s:actionmessage />
		<div class="jumbotron">
			<h1>Airline</h1>
			<h2>NaranAir Airline</h2>
			<p>Here is the information of all the routes of the airline in a
				datatable</p>
			<div class="container-fluid" id="routesMap">

				<p>Here the map</p>
			</div>

		</div>
	</section>

</div>