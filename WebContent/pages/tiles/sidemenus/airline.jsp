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
			<li><s:a action="airplanes" namespace="/airline">
					<i class="fa fa-plane fa-fw"></i>
					<s:text name="global.airplanes" />
				</s:a></li>
			<li><s:a action="routes" namespace="/airline">
					<i class="fa fa-exchange fa-fw"></i>
					<s:text name="global.routes" />
				</s:a></li>
				
			<li><s:a action="routeStats" namespace="/airline">
					<i class ="fa fa-bar-chart-o fa-fw"></i>
					<s:text name="global.routeStats" />
				</s:a></li>
		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->