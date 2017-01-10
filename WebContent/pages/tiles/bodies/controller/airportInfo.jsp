<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div id="airportInfoFilter">
	<div class="dropdown center-block" id="terminalDrop">
		<button class="btn btn-primary dropdown-toggle" type="button"
			id="dropdownTerminal" data-toggle="dropdown">
			Terminal <span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<s:iterator value="terminalList">
				<li><a
					href="javascript:changeTerminalZoom('<s:property value="positionNode.positionX"/>', '<s:property value="positionNode.positionY"/>', 'terminal')"><s:property
							value="name"></s:property></a></li>
			</s:iterator>
		</ul>
	</div>

	<div class="dropdown center-block" id="gateDrop">
		<button class="btn btn-primary dropdown-toggle" type="button"
			id="dropdownGate" data-toggle="dropdown">
			Gate <span class="caret"></span>
		</button>
		<ul class="dropdown-menu multi-level" role="menu">
			<li class="dropdown-submenu"><a tabindex="-1" href="#">A
					Gates</a>
				<ul class="dropdown-menu">
					<s:iterator value="gatesList">
					<s:if test="%{number <= 23 && number >=1}">
						<li><a tabindex="-1"
							href="javascript:changeTerminalZoom('<s:property value="positionNode.positionX"/>', '<s:property value="positionNode.positionY"/>', 'gate')"><s:property value="positionNode.name"></s:property></a></li>
					</s:if>
					</s:iterator>
				</ul></li>
						<li class="dropdown-submenu"><a tabindex="-1" href="#">B
					Gates</a>
				<ul class="dropdown-menu">
					<s:iterator value="gatesList">
					<s:if test="%{number <= 48 && number >=32}">
						<li><a tabindex="-1"
							href="javascript:changeTerminalZoom('<s:property value="positionNode.positionX"/>', '<s:property value="positionNode.positionY"/>', 'gate')"><s:property value="positionNode.name"></s:property></a></li>
					</s:if>
					</s:iterator>
				</ul></li>
						<li class="dropdown-submenu"><a tabindex="-1" href="#">C
					Gates</a>
				<ul class="dropdown-menu">
					<s:iterator value="gatesList">
					<s:if test="%{number <= 66 && number >=52}">
						<li><a tabindex="-1"
							href="javascript:changeTerminalZoom('<s:property value="positionNode.positionX"/>', '<s:property value="positionNode.positionY"/>', 'gate')"><s:property value="positionNode.name"></s:property></a></li>
					</s:if>
					</s:iterator>
				</ul></li>
		</ul>
	</div>
</div>

<div id="map" class="map"></div>