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
		data-toggle="dropdown" href="#"> <s:if
				test="%{#session.user != null}">
				<s:property value="#session.user.username" />
			</s:if><i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
	</a>
		<ul class="dropdown-menu dropdown-user">
			<s:if test="%{#session.user != null}">
				<li><a href="#"><i class="fa fa-user fa-fw"></i> User
						Profile</a></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a></li>
				<li class="divider"></li>
				<li><s:a action="logout"><i class="fa fa-sign-out fa-fw"></i>
						Logout</s:a></li>
			</s:if>
			<s:else>
				<li><s:a action="login"><i class="fa fa-sign-in fa-fw"></i>
						Login</s:a></li>
				<li><s:a action="register"><i class="fa fa-pencil-square-o fa-fw"></i>
						Register</s:a></li>
			</s:else>
			<li class="divider"></li>
			<li><s:a class="center-block"
					href="https://travis-ci.org/Maracars/POPBL5">
					<img class="center-block" alt="build stability"
						src="https://travis-ci.org/Maracars/POPBL5.svg?branch=master" />
				</s:a></li>
			<li><a class="center-block"
				href="https://www.codacy.com/app/Maracars/POPBL5?utm_source=www.github.com&amp;utm_medium=referral&amp;utm_content=Maracars/POPBL5&amp;utm_campaign=Badge_Grade"><img
					class="center-block"
					src="https://api.codacy.com/project/badge/Grade/4924993f790f4bd584d0c8516d553a9b" /></a></li>
		</ul> <!-- /.dropdown-user --></li>
	<!-- /.dropdown -->
</ul>
<!-- /.navbar-top-links -->