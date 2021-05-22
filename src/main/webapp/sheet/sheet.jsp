<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("sheet")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/admin"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("sheet")%></h2>
</div>
<div>
    <h2><%=loc.getString("budget")%></h2>
    <table>
        <tr>
            <th><%=loc.getString("last_name")%></th>
            <th><%=loc.getString("first_name")%></th>
            <th><%=loc.getString("middle_name")%></th>
            <th><%=loc.getString("email")%></th>
            <th><%=loc.getString("district")%></th>
            <th><%=loc.getString("city")%></th>
            <th><%=loc.getString("school")%></th>
        </tr>
        <c:forEach var="item" items="${budget}">
            <tr>
                <td><%=loc.getString("faculty")%>: ${item.key}</td>
                <td/>
                <td/>
                <td/>
                <td/>
                <td/>
                <td/>
            </tr>
            <c:forEach var="student" items="${item.value}">
                <tr>
                    <td>
                        ${student.lastName}
                    </td>
                    <td>
                        ${student.firstName}
                    </td>
                    <td>
                        ${student.middleName}
                    </td>
                    <td>
                        ${student.email}
                    </td>
                    <td>
                        ${student.district}
                    </td>
                    <td>
                        ${student.city}
                    </td>
                    <td>
                        ${student.school}
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
<div>
    <h2><%=loc.getString("contract")%></h2>
    <table>
        <tr>
            <th><%=loc.getString("last_name")%></th>
            <th><%=loc.getString("first_name")%></th>
            <th><%=loc.getString("middle_name")%></th>
            <th><%=loc.getString("email")%></th>
            <th><%=loc.getString("district")%></th>
            <th><%=loc.getString("city")%></th>
            <th><%=loc.getString("school")%></th>
        </tr>
        <c:forEach var="item" items="${contract}">
            <tr>
                <td><%=loc.getString("faculty")%>: ${item.key}</td>
                <td/>
                <td/>
                <td/>
                <td/>
                <td/>
                <td/>
            </tr>
            <c:forEach var="student" items="${item.value}">
                <tr>
                    <td>
                        ${student.lastName}
                    </td>
                    <td>
                        ${student.firstName}
                    </td>
                    <td>
                        ${student.middleName}
                    </td>
                    <td>
                        ${student.email}
                    </td>
                    <td>
                        ${student.district}
                    </td>
                    <td>
                        ${student.city}
                    </td>
                    <td>
                        ${student.school}
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</body>
</html>