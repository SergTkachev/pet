package com.servlet;

import com.model.User;
import com.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class FileDownloadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            FileService.getInstance().downloadFile(req, resp);
        } catch (WebApplicationException ex) {
            req.getSession().setAttribute("err", ex.getCause().getMessage());
            resp.sendRedirect(((User) req.getSession().getAttribute("user")).isAdmin() ? "/studentlist" : "/scanupload");
        }
    }
}