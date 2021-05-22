<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("create_faculty_exam")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/admin"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("create_faculty_exam")%></h2>
</div>
<div id="divTable">
    <form id="myform" action="${pageContext.request.contextPath}/facultyexamcreate" method="post">
        <table>
            <tr>
                <td><%=loc.getString("name")%></td>
                <td><input id="name" name="name" value="${facultyexam.exam}"/></td>
            </tr>
        </table>
        <div>
            <input type="submit" value='<%=loc.getString("create")%>'>
        </div>
    </form>
</div>
</body>
</html>