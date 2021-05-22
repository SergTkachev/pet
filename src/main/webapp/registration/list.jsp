<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("faculties_registration_list")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/login"><%=loc.getString("logout")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("faculties_registration_list")%></h2>
</div>
<div id="divTable">
    <table>
        <tr>
            <th><a href="${pageContext.request.contextPath}/facultysort?page=registrationlist&column=name"><%=loc.getString("name")%></a></th>
            <th><a href="${pageContext.request.contextPath}/facultysort?page=registrationlist&column=budget"><%=loc.getString("budget")%></a>
            </th>
            <th><a href="${pageContext.request.contextPath}/facultysort?page=registrationlist&column=common"><%=loc.getString("common")%></a>
            </th>
            <th/>
        </tr>
        <c:forEach var="faculty" items="${registrations}">
            <tr>
                <td>
                    ${faculty.name}
                </td>
                <td>
                    ${faculty.budget}
                </td>
                <td>
                    ${faculty.common}
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/registration?selected_id=${faculty.id}"><%=loc.getString("register")%></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>