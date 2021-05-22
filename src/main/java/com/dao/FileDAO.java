package com.dao;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileDAO extends CommonDAO {
    public static final String UPLOAD_DIRECTORY = "../../upload";
    private static FileDAO instance;

    private FileDAO() {

    }

    public static synchronized FileDAO getInstance() {
        if (instance == null) {
            instance = new FileDAO();
        }
        return instance;
    }

    public void uploadFile(FileItem fileItem, String rootPath, String userId) {
        String uploadPath = rootPath + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            fileItem.write(new File(uploadPath + File.separator + userId + ".jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        String filename = context.getRealPath("")
                + File.separator
                + UPLOAD_DIRECTORY
                + File.separator
                + req.getParameter("scan_id")
                + ".jpg";
        resp.setContentType(context.getMimeType(filename));
        File file = new File(filename);
        resp.setContentLength((int) file.length());
        FileInputStream in = new FileInputStream(file);
        OutputStream out = resp.getOutputStream();
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
    }
}