package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FacultySortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("fs_column") == null) {
            req.getSession().setAttribute("fs_column", "name");
        }
        if (req.getSession().getAttribute("fs_direction") == null) {
            req.getSession().setAttribute("fs_direction", "ASC");
        }
        String page = req.getParameter("page");
        String column = req.getParameter("column");
        String fsColumn = (String) req.getSession().getAttribute("fs_column");
        String fsDirection = (String) req.getSession().getAttribute("fs_direction");
        if (column.equalsIgnoreCase(fsColumn)) {
            req.getSession().setAttribute("fs_direction", "ASC".equalsIgnoreCase(fsDirection) ? "DESC" : "ASC");
        } else {
            req.getSession().setAttribute("fs_column", column);
            req.getSession().setAttribute("fs_direction", "ASC");
        }
        resp.sendRedirect("/" + page);
    }
}
