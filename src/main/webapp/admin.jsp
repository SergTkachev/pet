<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("admin_main_page")%></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/login"><%=loc.getString("logout")%></a>
    <br>
</div>
<div>
    <br>
    <br>
    <h1><%=loc.getString("admin_main_page")%></h1>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/facultycreate"><%=loc.getString("create_new_faculty")%></a>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/facultylist"><%=loc.getString("view_update_delete_faculties")%></a>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/studentlist"><%=loc.getString("block_unblock_students")%></a>
    <br>
    <br>
    <a href="${pageContext.request.contextPath}/sheet"><%=loc.getString("create_sheet")%></a>
</div>
</body>
</html>