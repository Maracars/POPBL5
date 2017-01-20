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
			<li><s:a action="flights" namespace="/">
					<i class="fa fa-plane fa-fw"></i>
					<s:text name="global.flights" />
				</s:a></li>

			<s:if
				test="%{#session.user != null && #session.user instanceof domain.model.users.Admin}">
				<li><s:a action="log" namespace="/">
						<i class="fa fa-bug fa-fw"></i>
						<s:text name="global.log" />
					</s:a></li>

			</s:if>

			<!--  <li><s:a action="stats" namespace="/">
					<i class="fa fa-bar-chart-o fa-fw"></i>
					<s:text name="global.stats" />
				</s:a></li> -->

		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
