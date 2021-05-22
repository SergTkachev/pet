package com.servlet;

import com.model.User;
import com.service.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        req.getSession().setAttribute("registrations", FacultyService.getInstance().getRegistrations(
                ((User) req.getSession().getAttribute("user")).getId(),
                (String) req.getSession().getAttribute("fs_column"),
                (String) req.getSession().getAttribute("fs_direction"))
        );
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/registration/list.jsp").forward(req, resp);
    }
}
