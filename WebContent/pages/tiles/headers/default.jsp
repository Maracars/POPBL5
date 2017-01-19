<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="navbar-header">
	<button type="button" class="navbar-toggle" data-toggle="collapse"
		data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
		<span class="icon-bar"></span> <span class="icon-bar"></span>
	</button>
	<s:a class="navbar-brand" action="index" namespace="/">
		<img style="height: 100%; display: inline-block;" alt="Naranair logo"
			src="<s:url value="/rsc/img/naranair.png"/>">
		<h2
			style="color: white; display: inline-block; font-size: 100%; margin: 0px auto;">Naranair</h2>
		<h3 style="display: inline-block; font-size: 80%; margin: 0px auto;">
			<s:text name="global.subtitle"/>
		</h3>
	</s:a>
</div>
<!-- /.navbar-header -->

<ul class="nav navbar-top-links navbar-right">
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
			<i class="fa fa-caret-down"></i>
	</a>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <s:if
				test="%{#session.user != null}">
				<s:property value="#session.user.username" />
			</s:if><i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
	</a>
		<ul class="dropdown-menu dropdown-user">
			<s:if test="%{#session.user != null}">
				<li><s:a action="%{'u/' + #session.user.username}" namespace="/"><i class="fa fa-user fa-fw"></i> <s:text name="global.userProfile"/></s:a></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i> <s:text name="global.settings"/></a></li>
				<s:if test="%{#session.user instanceof domain.model.users.Admin}">
					<li><s:a action="accountManager" namespace="/">
							<i class="fa fa-group fa-fw"></i> <s:text name="global.accountManager"/></s:a></li>
				</s:if>
				<li class="divider"></li>
				<li><s:a action="logout" namespace="/">
						<i class="fa fa-sign-out fa-fw"></i>
						<s:text name="global.logout"/></s:a></li>
			</s:if>
			<s:else>
				<li><s:a action="login" namespace="/">
						<i class="fa fa-sign-in fa-fw"></i>
						<s:text name="global.login"/></s:a></li>
				<li><s:a action="register/Passenger">
						<i class="fa fa-pencil-square-o fa-fw"></i>
						<s:text name="global.register"/></s:a></li>
			</s:else>
			<li class="divider"></li>
			<li><s:a class="center-block"
					href="https://travis-ci.org/Maracars/POPBL5">
					<img class="center-block" alt="build stability"
						src="https://travis-ci.org/Maracars/POPBL5.svg?branch=Development" />
				</s:a></li>
			<li><a class="center-block"
				href="https://www.codacy.com/app/Maracars/POPBL5?utm_source=www.github.com&amp;utm_medium=referral&amp;utm_content=Maracars/POPBL5&amp;utm_campaign=Badge_Grade">
				<img class="center-block"  alt="coverage quality"
					src="https://api.codacy.com/project/badge/Grade/4924993f790f4bd584d0c8516d553a9b" /></a></li>
		</ul> <!-- /.dropdown-user --></li>
	<!-- /.dropdown -->
	<s:url var="indexEN" namespace="/" action="locale">
		<s:param name="request_locale">en</s:param>
	</s:url>
	<s:url var="indexES" namespace="/" action="locale">
		<s:param name="request_locale">es</s:param>
	</s:url>
	<s:url var="indexEU" namespace="/" action="locale">
		<s:param name="request_locale">is</s:param>
	</s:url>

	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <i class="fa fa-globe fa-fw"></i>
			<i class="fa fa-caret-down"></i>
	</a>
		<ul class="dropdown-menu dropdown" id="alertsList">
			<li><s:a href="%{indexEN}">
					<img height="20" width="40" alt="English"
						src="<s:url value="/rsc/img/english.png"/>">
					English
				</s:a></li>
			<li><s:a href="%{indexES}">
					<img height="20" width="40" alt="Español"
						src="<s:url value="/rsc/img/spanish.png"/>">
					Castellano
				</s:a></li>
			<li><s:a href="%{indexEU}">
					<img height="20" width="40" alt="Euskara"
						src="<s:url value="/rsc/img/basque.png"/>">
					Euskara
				</s:a></li>
		</ul></li>
</ul>
<!-- /.navbar-top-links -->