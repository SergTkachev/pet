<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("faculty_exams")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/admin"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("faculty_exams")%></h2>
    <a href="${pageContext.request.contextPath}/facultyexamcreate?faculty_id=${faculty.id}"><%=loc.getString("create_new_exam")%></a>
</div>
<div id="divTable">
    <form id="myform" action="${pageContext.request.contextPath}/facultyupdate" method="post">
        ${faculty.name}
        <table>
            <input type="hidden" id="id" name="id" value="${faculty.id}"/>
            <%int i = 0;%>
            <c:forEach var="exam" items="${faculty.exams}">
                <tr>
                    <td>
                        <input type="hidden" name="<%=i++%>" value="${exam.id}"/>
                        <input type="checkbox" name="checkbox${exam.id}" checked/>
                    </td>
                    <td>
                        <input name="name${exam.id}" value="${exam.exam}"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/facultyexamdelete?faculty_id=${faculty.id}&exam_id=${exam.id}"><%=loc.getString("delete")%></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <input type="submit" value='<%=loc.getString("save")%>'>
        </div>
    </form>
</div>
</body>
</html>