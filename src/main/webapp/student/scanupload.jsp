<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("upload_scan")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/login"><%=loc.getString("login")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("upload_scan")%></h2>
</div>
<form id="myform" action="${pageContext.request.contextPath}/scanupload" method="post" enctype="multipart/form-data">
    <div id="divTable">
        <table>
            <tr>
                <td>
                    <input type="file" accept=".jpg" id="upload" name="upload" style="visibility: hidden; width: 1px; height: 1px;" onchange="document.getElementById('myform').submit();"/>
                    <a href="" onclick="document.getElementById('upload').click(); return false"><%=loc.getString("upload_scan")%> (.jpg)</a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="/registrationlist"><%=loc.getString("skip")%></a>
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>