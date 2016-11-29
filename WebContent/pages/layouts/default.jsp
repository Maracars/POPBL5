<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<link rel='shortcut icon' type='image/png' href='rsc/img/favicon.png' />

<sj:head />
<sb:head />
<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="css/metisMenu.min.css" />
<link rel="stylesheet" type="text/css" href="css/sb-admin-2.min.css" />

<script type="text/javascript" src="js/metisMenu.min.js"></script>
<script type="text/javascript" src="js/sb-admin-2.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Naranair - <tiles:insertAttribute name="title"
		ignore="true" /></title>
</head>
<body>

	<div id="wrapper">
		<nav style="margin: 0px; background-color: black;"
			class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="sidemenu" />
		</nav>
		<div id="page-wrapper" style="display: none; padding-top: 20px;">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		$('#page-wrapper').fadeIn(1000);
	})
	</script>

</body>
</html>