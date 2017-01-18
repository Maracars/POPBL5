<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel='shortcut icon' type='image/png'
	href="<s:url value="/rsc/img/favicon.png"/>" />

<tiles:importAttribute name="stylesheets" ignore="true" />

<sj:head />

<sb:head />
<s:if test="%{#attr.stylesheets != null}">
	<s:iterator value="#attr.stylesheets" var="cssValue">
		<link rel="stylesheet" type="text/css"
			href="<s:url value="%{cssValue}"/>" />
	</s:iterator>
</s:if>

<tiles:importAttribute name="javascripts" ignore="true" />
<s:if test="%{#attr.javascripts != null}">
	<s:iterator value="#attr.javascripts" var="jsValue">
		<script type="text/javascript" src="<s:url value="%{jsValue}"/>"></script>
	</s:iterator>
</s:if>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<s:url value="/css/bootstrap.css"/>" />

<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/controller.css"/>" />


<script type="text/javascript"
	src="https://openlayers.org/en/v3.20.0/build/ol.js"></script>
<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>

<script src="<s:url value="/js/listener.js"/>"></script>
<script src="<s:url value="/js/bootstrap-notify.min.js"/>"></script>
<script src="<s:url value="/js/usernameCheck.js"/>"></script>
<script src="<s:url value="/js/app.min.js"/>"></script>

<link href="<s:url value="/css/animate.css"/>" rel="stylesheet">
<link href="<s:url value="/css/naranair.css"/>" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="<s:url value="/css/AdminLTE.min.css"/>" />
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
<link rel="stylesheet"
	href="<s:url value="/css/skins/skin-blue.min.css"/>" />





<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<tiles:importAttribute name="title" />
<title>Naranair - <s:property value="%{getText(#attr.title)}" />
</title>



</head>

<body class="hold-transition skin-blue sidebar-mini">

	<input type="hidden" id="listenerRole" name="listenerRole"
		value="${sessionScope.listenerRole}">
	<input type="hidden" id="listenerUser" name="listenerUser"
		value="${sessionScope.listenerUser}">

	<div id="wrapper">
		<header class="main-header">
			<!-- Logo -->
			<!-- Logo -->
			<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>N</b>air</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>Naranair</b></span>
			</a>

			<nav class="navbar navbar-static-top" role="navigation">
				<tiles:insertAttribute name="header" />
			</nav>
		</header>
		<tiles:insertAttribute name="sidemenu" />
		<div id="page-wrapper"
			style="display: none; padding-top: 20px; padding-bottom: 10%;">
			<tiles:insertAttribute name="body" />
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#page-wrapper').fadeIn(500);
			$('#focused').focus();
		})
	</script>

</body>
</html>