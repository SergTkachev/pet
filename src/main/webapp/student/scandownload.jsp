<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("scan")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <c:choose>
        <c:when test="${user.admin}">
            <a href="${pageContext.request.contextPath}/studentlist"><%=loc.getString("back")%></a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/registrationlist"><%=loc.getString("continue")%></a>
        </c:otherwise>
    </c:choose>
    <br>
</div>
<div>
    <div>
        <img src="/scandownload?scan_id=${scan_id}"/>
    </div>
</div>
</body>
</html>