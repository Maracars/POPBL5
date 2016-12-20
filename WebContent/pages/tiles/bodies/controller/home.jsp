<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<!-- Internalizaziñua falta da hemen -->

<ul class="nav nav-pills nav-justified" id="flightsNavbar">
  <li class="active"><a href="#">All</a></li>
  <li><a href="#">Arrival</a></li>
  <li><a href="#">Departure</a></li>
  <li><a href="#">Outside</a></li>
</ul>

<div class="container-fluid">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron">
		<h1>Controller</h1>
		<h2>Airport flights management</h2>
		<p>Here are visualized all the flights of the airport in a map
		<hr>
		<em> designed by MARACARS</em>
		</p>

	</div>
</div>