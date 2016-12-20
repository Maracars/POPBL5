<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div id="airportInfoFilter">
<div class="dropdown" id="terminalDrop">
  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
   	Terminal
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <li><a href="#">Terminal 1</a></li>
    <li><a href="#">Terminal 2</a></li>
    <li><a href="#">Terminal 3</a></li>
    <li><a href="#">Terminal 4</a></li>
  </ul>
</div>

<div class="dropdown" id="gateDrop">
  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
   	Gate
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <li><a href="#">Gate 1</a></li>
    <li><a href="#">Gate 2</a></li>
    <li><a href="#">Gate 3</a></li>
    <li><a href="#">Gate 4</a></li>
  </ul>
</div>
</div>

<div class="container-fluid">
	<s:actionerror />
	<s:actionmessage />
	<div class="jumbotron">
		<h1>Controller</h1>
		<h2>Airport flights management</h2>
		<p>Here are visualized all the flights of the airport in a map more concretely
		<hr>
		<em> designed by MARACARS</em>
		</p>

	</div>
</div>