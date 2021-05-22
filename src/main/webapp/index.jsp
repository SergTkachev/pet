<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.io.File" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("main_page")%></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div>
    <table>
        <tr>
            <td><a href="${pageContext.request.contextPath}/localization?lang=en">English</a></td>
            <td><a href="${pageContext.request.contextPath}/localization?lang=ru">Русский</a></td>
        </tr>
    </table>
    <h2><%=loc.getString("main_page")%></h2>
</div>
<form id="myform" action="${pageContext.request.contextPath}/login" method="post">
    <div id="divTable">
        <table>
            <tr>
                <td><%=loc.getString("email")%></td>
                <td><input id="email" name="email"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("password")%></td>
                <td><input id="password" name="password"/></td>
            </tr>
        </table>
    </div>
    <div>
        <input type="submit" value='<%=loc.getString("login")%>'/>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/signup"><%=loc.getString("signup")%></a>
    </div>
</form>
</body>
</html>