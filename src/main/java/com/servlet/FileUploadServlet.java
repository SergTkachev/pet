package com.servlet;

import com.model.User;
import com.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("fileUploadServletWasSubmitted");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/student/scanupload.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("fileUploadServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("fileUploadServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            FileService.getInstance().uploadFile(req);
            req.getSession().setAttribute("scan_id", ((User) req.getSession().getAttribute("user")).getId());
            getServletContext().getRequestDispatcher(getServletContext().getContextPath()
                    + "/student/scandownload.jsp").forward(req, resp);
        } catch (WebApplicationException ex) {
            req.getSession().setAttribute("err", ex.getCause().getMessage());
            resp.sendRedirect("/scanupload");
        }
    }
}