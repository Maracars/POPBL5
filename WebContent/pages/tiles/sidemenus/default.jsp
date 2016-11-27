<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li class="sidebar-search"><s:form action="search"
					enctype="multipart/form-data" theme="bootstrap"
					cssClass="form-horizontal">
					<div class="input-group custom-search-form">
						<input type="text" class="form-control" name="searchvalue"
							placeholder="Search..." /> <span class="input-group-btn">
							<button class="btn btn-default" type="button">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</s:form></li>
			<li><s:a action="index">
					<i class="fa fa-home fa-fw"></i>
							Home</s:a></li>
			<li><s:a action="flights">
					<i class="fa fa-plane fa-fw"></i>
							Flights</s:a></li>
			<li><s:a action="stats">
					<i class="fa fa-bar-chart-o fa-fw"></i>
							Stats</s:a></li>

		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
