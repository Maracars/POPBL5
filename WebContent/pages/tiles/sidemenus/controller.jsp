<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			
			<li><s:a action="index" namespace="/">
					<i class="fa fa-home fa-fw"></i>
					<s:text name="global.home" />
				</s:a></li>
			<li><s:a action="airport" namespace="/controller">
					<i class="fa fa-suitcase fa-fw"></i>
					<s:text name="global.airport" />
				</s:a></li>
			<li><s:a action="flights" namespace="/controller">
					<i class="fa fa-plane fa-fw"></i>
					<s:text name="global.flightsInfo" />
				</s:a></li>
				
			<li><s:a action="laneAndGate" namespace="/controller">
					<s:text name="global.laneAndGate" />
				</s:a></li>
		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->