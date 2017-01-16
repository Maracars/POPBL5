<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<link rel='shortcut icon' type='image/png'
	href="<s:url value="/rsc/img/favicon.png"/>" />

<tiles:importAttribute name="stylesheets" ignore="true" />
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
<s:else>
	<sj:head />
</s:else>

<sb:head />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/font-awesome.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/metisMenu.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/sb-admin-2.min.css"/>" />

<script type="text/javascript"
	src="<s:url value="/js/metisMenu.min.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/sb-admin-2.min.js"/>"></script>
<script type="text/javascript"
	src="https://openlayers.org/en/v3.20.0/build/ol.js"></script>


<link href="<s:url value="/css/animate.css"/>" rel="stylesheet">
<link href="<s:url value="/css/naranair.css"/>" rel="stylesheet">

<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>


<script src="<s:url value="/js/listener.js"/>"></script>
<script src="<s:url value="/js/bootstrap-notify.min.js"/>"></script>
<script src="<s:url value="/js/usernameCheck.js"/>"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<tiles:importAttribute name="title" />
<title>Naranair - <s:property value="%{getText(#attr.title)}" />
</title>



</head>
<body onload="onLoadFunction()">

	<input type="hidden" id="listenerRole" name="listenerRole"
		value="${sessionScope.listenerRole}">
	<input type="hidden" id="listenerUser" name="listenerUser"
		value="${sessionScope.listenerUser}">

	<div id="wrapper">
		<nav style="margin: 0px; background-color: black; margin-bottom: 0;"
			class="navbar navbar-default navbar-static-top">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="sidemenu" />
		</nav>
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