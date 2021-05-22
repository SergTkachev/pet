package com.service;

import com.dao.FileDAO;
import com.model.User;
import com.validation.FileValidator;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FileService extends HttpServlet {
    private static FileService instance;
    private FileDAO fileDAO;
    private FileValidator fileValidator;

    private FileService() {
        fileDAO = FileDAO.getInstance();
        fileValidator = FileValidator.getInstance();
    }

    public static synchronized FileService getInstance() {
        if (instance == null) {
            instance = new FileService();
        }
        return instance;
    }

    public void uploadFile(HttpServletRequest req) {
        FileItem fileItem = fileValidator.isUploadedFileValid(req);
        fileDAO.uploadFile(fileItem,
                req.getSession().getServletContext().getRealPath(""),
                ((User) req.getSession().getAttribute("user")).getId());
    }

    public void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileValidator.isDownloadingFileValid(req.getSession().getServletContext().getRealPath(""),
                req.getParameter("scan_id"));
        fileDAO.downloadFile(req, resp);
    }
}