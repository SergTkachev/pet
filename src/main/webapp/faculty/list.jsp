<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=loc.getString("faculties_list")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/admin"><%=loc.getString("home")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("faculties_list")%></h2>
    <a href="${pageContext.request.contextPath}/facultycreate"><%=loc.getString("create_new_faculty")%></a>
</div>
<form id="myform" action="${pageContext.request.contextPath}/facultylist" method="post">
    <input type="hidden" name="listsize" value="${faculties.size()}"/>
    <div id="divTable">
        <table>
            <tr>
                <th><%=loc.getString("bulk")%></th>
                <th>
                    <a href="${pageContext.request.contextPath}/facultysort?page=facultylist&column=name"><%=loc.getString("name")%></a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/facultysort?page=facultylist&column=budget"><%=loc.getString("budget")%></a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/facultysort?page=facultylist&column=common"><%=loc.getString("common")%></a>
                </th>
                <th/>
                <th/>
            </tr>
            <%int i = 0;%>
            <c:forEach var="faculty" items="${faculties}">
                <tr>
                    <td>
                        <input type="hidden" name="<%=i++%>" value="${faculty.id}"/>
                        <input type="checkbox" name="checkbox${faculty.id}" checked/>
                    </td>
                    <td>
                        <input name="name${faculty.id}" value="${faculty.name}"/>
                    </td>
                    <td>
                        <input name="budget${faculty.id}" value="${faculty.budget}"/>
                    </td>
                    <td>
                        <input name="common${faculty.id}" value="${faculty.common}"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/facultyupdate?selected_id=${faculty.id}">
                            <%=loc.getString("edit")%>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/facultydelete?selected_id=${faculty.id}"
                           onclick="return confirm('<%=loc.getString("are_you_sure_you_want_to_continue")%>')">
                            <%=loc.getString("delete")%>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="divButtons">
        <input type="submit" value='<%=loc.getString("save")%>'/>
    </div>
</form>
</body>
</html>