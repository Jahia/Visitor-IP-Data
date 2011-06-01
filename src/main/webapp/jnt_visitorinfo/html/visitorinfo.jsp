<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions"%>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="visitorinfo"	uri="http://www.jahia.org/tags/visitorinfo"%>

<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>

<!-- host -->
<c:set var="hoststr" value="host"/>
<c:set var="host" value="${visitorinfo:header(renderContext.user, renderContext.request, hoststr)}"/>
host = ${host}
<BR>

<!-- useragent -->
<c:set var="useragentstr" value="User-Agent"/>
<c:set var="useragent" value="${visitorinfo:header(renderContext.user, renderContext.request, useragentstr)}"/>
useragent = ${useragent}
<BR>

<!-- accept -->
<c:set var="acceptstr" value="accept"/>
<c:set var="accept" value="${visitorinfo:header(renderContext.user, renderContext.request, acceptstr)}"/>
accept = ${accept}
<BR>

<!-- accept-language -->
<c:set var="acceptlanguagestr" value="accept-language"/>
<c:set var="acceptlanguage" value="${visitorinfo:header(renderContext.user, renderContext.request, acceptlanguagestr)}"/>
accept-language = ${acceptlanguage}
<BR>

<!-- accept-encoding -->
<c:set var="acceptencodingstr" value="accept-encoding"/>
<c:set var="acceptencoding" value="${visitorinfo:header(renderContext.user, renderContext.request, acceptencodingstr)}"/>
accept-encoding = ${acceptencoding}
<BR>

<!-- accept-charset -->
<c:set var="acceptcharsetstr" value="accept-charset"/>
<c:set var="acceptcharset" value="${visitorinfo:header(renderContext.user, renderContext.request, acceptencodingstr)}"/>
accept-charset = ${acceptcharset}
<BR>

<!-- keep-alive -->
<c:set var="keepalivestr" value="keep-alive"/>
<c:set var="keepalive" value="${visitorinfo:header(renderContext.user, renderContext.request, keepalivestr)}"/>
keep-alive = ${keepalive}
<BR>

<!-- connection -->
<c:set var="connectionstr" value="connection"/>
<c:set var="connection" value="${visitorinfo:header(renderContext.user, renderContext.request, connectionstr)}"/>
connection = ${connection}
<BR>

<!-- referer -->
<c:set var="refererstr" value="referer"/>
<c:set var="referer" value="${visitorinfo:header(renderContext.user, renderContext.request, refererstr)}"/>
referer = ${referer}
<BR>

<!-- cookie -->
<c:set var="cookiestr" value="cookie"/>
<c:set var="cookie" value="${visitorinfo:header(renderContext.user, renderContext.request, cookiestr)}"/>
cookie = ${cookie}
<BR>

<!-- authorization -->
<c:set var="authorizationstr" value="authorization"/>
<c:set var="authorization" value="${visitorinfo:header(renderContext.user, renderContext.request, authorizationstr)}"/>
authorization = ${authorization}
<BR>

<!-- remoteHost -->
<c:set var="remoteHost" value="${visitorinfo:remoteHost(renderContext.user, renderContext.request)}"/>
remoteHost = ${remoteHost}
<BR>

<!-- remoteAddr -->
<c:set var="remoteAddr" value="${visitorinfo:remoteAddr(renderContext.user, renderContext.request)}"/>
remoteAddr = ${remoteAddr}
<BR>

<!-- remotePort -->
<c:set var="remotePort" value="${visitorinfo:remotePort(renderContext.user, renderContext.request)}"/>
remotePort = ${remotePort}
<BR>

<!-- remoteUser -->
<c:set var="remoteUser" value="${visitorinfo:remoteUser(renderContext.user, renderContext.request)}"/>
remoteUser = ${remoteUser}
<BR>

<!-- X-Forwarded-For -->
<c:set var="xforwardedfor" value="${visitorinfo:xForwardFor(renderContext.user, renderContext.request)}"/>
xforwardedfor = ${xforwardedfor}
<BR>

<!-- User Principal -->
<c:set var="userPrincipal" value="${visitorinfo:userPrincipal(renderContext.user, renderContext.request)}"/>
userPrincipal = ${userPrincipal}
<BR>

<!-- Path Info -->
<c:set var="pathInfo" value="${visitorinfo:pathInfo(renderContext.user, renderContext.request)}"/>
pathInfo = ${pathInfo}
<BR>

<!-- Country -->
<c:set var="country" value="${visitorinfo:country(renderContext.user, renderContext.request)}"/>
country = ${country}
<BR>

<!-- City -->
<c:set var="city" value="${visitorinfo:city(renderContext.user, renderContext.request)}"/>
city = ${city}
<BR>
