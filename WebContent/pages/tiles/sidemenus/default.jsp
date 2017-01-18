<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<aside class="main-sidebar">

	<section class="sidebar">

		<ul class="sidebar-menu">
			<li class="header"><s:text name="global.subtitle" /></li>

			<li><s:a action="index" namespace="/">
					<i class="fa fa-home fa-fw"></i>
					<span><s:text name="global.home" /></span>
				</s:a></li>
			<li><s:a action="flights" namespace="/">
					<i class="fa fa-plane fa-fw"></i>
					<span><s:text name="global.flights" /></span>
				</s:a></li>
			<!--  <li><s:a action="stats" namespace="/">
					<i class="fa fa-bar-chart-o fa-fw"></i>
					<s:text name="global.stats" />
				</s:a></li> -->
			<li><s:a action="log" namespace="/">
					<i class="fa fa-bug fa-fw"></i>
					<span><s:text name="global.log" /></span>
				</s:a></li>

		</ul>
	</section>
</aside>


