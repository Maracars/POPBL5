<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">

			<li><s:a action="index" namespace="/passenger">
					<i class="fa fa-home fa-fw"></i>
					<s:text name="global.home" />
				</s:a></li>
			<li><s:a action="bookFlight" namespace="/passenger">
					<i class="fa fa-suitcase fa-fw"></i>
					<s:text name="global.bookFlight" />
				</s:a></li>
			<li><s:a action="myFlights" namespace="/passenger">
					<i class="fa fa-plane fa-fw"></i>
					<s:text name="global.myFlights" />
				</s:a></li>
		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->