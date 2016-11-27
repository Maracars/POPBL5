<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="navbar-header">
	<button type="button" class="navbar-toggle" data-toggle="collapse"
		data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
		<span class="icon-bar"></span> <span class="icon-bar"></span>
	</button>
	<s:a class="navbar-brand" action="index">
		<img style="height: 100%; display: inline-block;" alt="Naranair logo"
			src="rsc/img/naranair.png">
		<h1
			style="color: white; display: inline-block; font-size: 100%; margin: 0px auto;">Naranair</h1>
		<h2 style="display: inline-block; font-size: 80%; margin: 0px auto;">Airport
			manager</h2>
	</s:a>
</div>
<!-- /.navbar-header -->

<ul class="nav navbar-top-links navbar-right">
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
			<i class="fa fa-caret-down"></i>
	</a>
		<ul class="dropdown-menu dropdown-alerts">
			<!-- Aqui las notificaciones -->
		</ul> <!-- /.dropdown-alerts --></li>
	<!-- /.dropdown -->
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
			<i class="fa fa-caret-down"></i>
	</a>
		<ul class="dropdown-menu dropdown-user">
			<li><a href="#"><i class="fa fa-user fa-fw"></i> User
					Profile</a></li>
			<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a></li>
			<li class="divider"></li>
			<li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i>
					Logout</a></li>
			<li class="divider"></li>
			<li><s:a class="center-block"
					href="https://travis-ci.org/Maracars/POPBL5">
					<img class="center-block" alt="build stability"
						src="https://travis-ci.org/Maracars/POPBL5.svg?branch=master" />
				</s:a></li>
		</ul> <!-- /.dropdown-user --></li>
	<!-- /.dropdown -->
</ul>
<!-- /.navbar-top-links -->