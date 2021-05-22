<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ResourceBundle loc = (ResourceBundle) request.getServletContext().getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        var $j = jQuery.noConflict();

        function addMark() {
            var index = 0;
            while (document.getElementById(index) !== undefined
            && document.getElementById(index) !== null) {
                index = index + 1;
            }
            var nameField = $j('<td/>').append($j('<input type="text" id="subject' + index + '" name="subject' + index + '"/>'));
            var markField = $j('<td/>').append($j('<input type="text" id="mark' + index + '" name="mark' + index + '"/>'));
            var removeLink = $j('<td/>').append($j('<a id="a' + index + '" href="#" onclick="removeMark(' + index + ')">Delete</a>'));
            $j('<tr id="' + index + '" name="\' + index + \'"/>').appendTo($j('#marks'))
                .append(nameField)
                .append(markField)
                .append(removeLink);
        }

        function removeMark(index) {
            $j('#' + index).remove();
            index++;
            while (document.getElementById(index) !== undefined
            && document.getElementById(index) !== null) {
                var elem = document.getElementById(index);
                elem.id = index - 1;
                elem.name = index - 1;
                elem = document.getElementById('subject' + index);
                elem.id = 'subject' + (index - 1);
                elem.name = 'subject' + (index - 1);
                elem = document.getElementById('mark' + index);
                elem.id = 'mark' + (index - 1);
                elem.name = 'mark' + (index - 1);
                elem = document.getElementById('a' + index);
                elem.id = 'a' + (index - 1);
                elem.setAttribute("onClick", "javascript: removeMark('" + (index - 1) + "');");
                index++;
            }
        }

    </script>
    <title><%=loc.getString("signup")%></title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/login"><%=loc.getString("login")%></a>
    <br>
</div>
<div>
    <h2><%=loc.getString("signup")%></h2>
</div>
<form id="myform" action="${pageContext.request.contextPath}/signup" method="post">
    <div id="divTable">
        <table>
            <tr>
                <td><%=loc.getString("email")%></td>
                <td><input id="email" name="email" value="${user.email}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("password")%></td>
                <td><input id="password" name="password"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("first_name")%></td>
                <td><input id="first_name" name="first_name" value="${user.firstName}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("middle_name")%></td>
                <td><input id="middle_name" name="middle_name" value="${user.middleName}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("last_name")%></td>
                <td><input id="last_name" name="last_name" value="${user.lastName}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("district")%></td>
                <td><input id="district" name="district" value="${user.district}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("city")%></td>
                <td><input id="city" name="city" value="${user.city}"/></td>
            </tr>
            <tr>
                <td><%=loc.getString("school")%></td>
                <td><input id="school" name="school" value="${user.school}"/></td>
            </tr>
        </table>
        <h3><%=loc.getString("marks")%>:</h3>
        <table id="marks">
            <tr>
                <th><%=loc.getString("subject")%></th>
                <th><%=loc.getString("mark")%></th>
            </tr>
            <%int i = 0;%>
            <c:forEach var="mark" items="${user.marks}">
                <tr id="<%=i%>" name="<%=i%>">
                    <td>
                        <input id="subject<%=i%>" name="subject<%=i%>" value="${mark.subject}"/>
                    </td>
                    <td>
                        <input id="mark<%=i%>" name="mark<%=i%>" value="${mark.mark}"/>
                    </td>
                    <td>
                        <a id="a<%=i%>" href="#" onclick="removeMark(<%=i++%>)"><%=loc.getString("delete")%></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div>
        <input type="button" value='<%=loc.getString("add_mark")%>' onclick="addMark()"/>
    </div>
    <br>
    <div>
        <input type="submit" value='<%=loc.getString("create")%>'/>
    </div>
</form>
</body>
</html>