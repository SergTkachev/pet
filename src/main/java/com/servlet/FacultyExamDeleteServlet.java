package com.servlet;

import com.service.FacultyExamService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class FacultyExamDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        try {
            FacultyExamService.getInstance().delete(req.getParameter("exam_id"));
        } catch (WebApplicationException ex) {
            req.getSession().setAttribute("err", ex.getResponse().getEntity().toString());
        }
        resp.sendRedirect("/facultyupdate?selected_id=" + req.getParameter("faculty_id"));
    }
}
