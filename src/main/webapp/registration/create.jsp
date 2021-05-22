<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("registration")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/registrationlist"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("registration")%></h2>
</div>
<div id="divTable">
    <form id="myform" action="${pageContext.request.contextPath}/registration" method="post">
        ${regfaculty.name}
        <table>
            <input type="hidden" id="id" name="id" value="${regfaculty.id}"/>
            <%int i = 0;%>
            <c:forEach var="exam" items="${regfaculty.exams}">
                <tr>
                    <td>
                        <input type="hidden" name="<%=i++%>" value="${exam.id}"/>
                        ${exam.exam}
                    </td>
                    <td>
                        <input name="exammark${exam.id}" value="${exam.exammark}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <input type="submit" value='<%=loc.getString("register")%>'>
        </div>
    </form>
</div>
</body>
</html>