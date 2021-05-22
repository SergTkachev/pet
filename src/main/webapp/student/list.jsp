<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("students_list")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/admin"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("students_list")%></h2>
</div>
<div id="divTable">
    <table>
        <tr>
            <th><%=loc.getString("last_name")%></th>
            <th><%=loc.getString("first_name")%></th>
            <th><%=loc.getString("middle_name")%></th>
            <th><%=loc.getString("email")%></th>
            <th><%=loc.getString("average")%></th>
            <th><%=loc.getString("blocked")%></th>
            <th/>
        </tr>
        <%int i = 0;%>
        <c:forEach var="student" items="${students}">
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
                    ${student.averageMark}
                </td>
                <td>
                    ${student.blocked}
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/studentblockunblock?selected_id=${student.id}">
                        <c:choose>
                            <c:when test="${student.blocked}">
                                <%=loc.getString("unblock")%>
                            </c:when>
                            <c:otherwise>
                                <%=loc.getString("block")%>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </td>
                <td>
                    <c:if test="${student.withScan}">
                        <a href="${pageContext.request.contextPath}/scanreview?scan_id=${student.id}"><%=loc.getString("scan")%></a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>