<%@ page import="org.jboss.portal.server.PortalConstants"%>
<%@ taglib uri="/WEB-INF/theme/portal-layout.tld" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>OpenQuote @major.version@.@minor.version@</title>
<meta http-equiv="Content-Type" content="text/html;" />
<!-- to correct the unsightly Flash of Unstyled Content. -->
<script type="text/javascript"></script>
<!-- IE fix for alpha in .PNGs -->
<!--[if lt IE 7]> 
	 <script defer type="text/javascript" src="/portal-core/js/pngfix.js"></script> 
	 <![endif]-->
<!-- use the renaissance theme if nothing else was defined for the portal or the page -->
<p:theme themeName="renaissance" />
<!-- inject header content that was generated by the portlets on the requested page -->
<p:headerContent />
</head>

<body id="body">
<p:region regionName='AJAXScripts' regionID='AJAXScripts' />
<%@include file="/layouts/common/modal.jsp"%>
<div id="portal-container">
<div id="sizer">
<div id="expander">
<div id="logoName"></div>
<table border="0" cellpadding="0" cellspacing="0" id="header-container">
	<tr>
		<td align="center" valign="top" id="header"><p:region
			regionName='dashboardnav' regionID='dashboardnav' /> <p:region
			regionName='navigation' regionID='navigation' />
		<div id="spacer"></div>
		</td>
	</tr>
</table>
<div id="content-container"><!-- Note: this construct assumes that the 'maximizedRegion' layout strategy was chosen for the layout, page or portal -->
<!-- the 'maximizedRegion' strategy assigns the maximized portlet to the 'maximized' region -->
<p:region regionName='maximized' regionID='regionMaximized' />
<hr class="cleaner" />
<div id="footer-container" class="portal-copyright">Powered by <a
	class="portal-copyright"
	href="http://openquote.opensourceinsurance.org">OpenQuote</a><br />
</div>
</div>
</div>

</div>
</div>
</div>
<p:region regionName='AJAXFooter' regionID='AJAXFooter' />
</body>
</html>
