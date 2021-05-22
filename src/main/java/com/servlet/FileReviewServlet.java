package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FileReviewServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().setAttribute("scan_id", req.getParameter("scan_id"));
        getServletContext().getRequestDispatcher(getServletContext().getContextPath()
                + "/student/scandownload.jsp").forward(req, resp);
    }
}