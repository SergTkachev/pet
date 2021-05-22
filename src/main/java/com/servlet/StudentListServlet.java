package com.servlet;

import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        req.getSession().setAttribute("students", UserService.getInstance().getAll(req.getSession().getServletContext().getRealPath("")));
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/student/list.jsp").forward(req, resp);
    }
}
