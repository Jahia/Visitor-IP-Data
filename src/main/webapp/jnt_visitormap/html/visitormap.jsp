<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr"%>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib"%>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions"%>
<%@ taglib prefix="ui" uri="http://www.jahia.org/tags/uiComponentsLib"%>
<%@ taglib prefix="visitorinfo"	uri="http://www.jahia.org/tags/visitorinfo"%>

<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>

<c:set var="location" value="${visitorinfo:gps(renderContext.user, renderContext.request)}"/>
<div class="location-static-map">
	<c:url var="mapUrl" value="http://maps.google.com/maps/api/staticmap">
		<c:param name="sensor" value="false" />
		<c:param name="size" value="300x300" />
		<c:param name="markers" value="${location}" />
		<c:param name="zoom" value="10"/>
	</c:url>
	<p class="location-static-map">
		<img src="${fn:escapeXml(mapUrl)}" title="I'm watching You" alt="I'm watching You" />
	</p>
</div>