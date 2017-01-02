<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div id="airportInfoFilter">
<div class="dropdown center-block" id="terminalDrop">
  <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownTerminal" data-toggle="dropdown">
   	Terminal
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuTerminal">
  	<s:iterator value="terminalList">
  	<li><a href="javascript:changeTerminalZoom('<s:property value="positionNode.positionX"/>', '<s:property value="positionNode.positionY"/>')"><s:property value="name"></s:property></a></li>
    </s:iterator>
  </ul>
</div>

<div class="dropdown center-block" id="gateDrop">
  <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownGate" data-toggle="dropdown">
   	Gate
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenuGate">
    <li><a href="#">Gate 1</a></li>
    <li><a href="#">Gate 2</a></li>
    <li><a href="#">Gate 3</a></li>
    <li><a href="#">Gate 4</a></li>
  </ul>
</div>
</div>

<div id="map" class="map"></div>