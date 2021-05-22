<%@ taglib uri="/WEB-INF/tag/user.tld" prefix="my" %>
<div id="divErrors" style="width: 100%;">
    <table>
        <tr>
            <td style="width: 50%;color: red">${err}</td>
            <td style="text-align:right">
                <my:user
                        firstName='<%=request.getSession().getAttribute("user") != null
                        && Integer.valueOf(((com.model.User) request.getSession().getAttribute("user")).getId()) > 0
                        ? ((com.model.User) request.getSession().getAttribute("user")).getFirstName()
                        : ""%>'
                        lastName='<%=request.getSession().getAttribute("user") != null
                        && Integer.valueOf(((com.model.User) request.getSession().getAttribute("user")).getId()) > 0
                        ? ((com.model.User) request.getSession().getAttribute("user")).getLastName()
                        : ""%>'/>
            </td>
        </tr>
    </table>
</div>
