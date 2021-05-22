package com.validation;

import com.sun.jersey.api.ParamException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.dao.FileDAO.UPLOAD_DIRECTORY;

public class FileValidator extends Validator {
    private static FileValidator instance;

    private FileValidator() {

    }

    public static synchronized FileValidator getInstance() {
        if (instance == null) {
            instance = new FileValidator();
        }
        return instance;
    }

    public FileItem isUploadedFileValid(HttpServletRequest req) {
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("scan_form_must_have_enctype_multipart_form_data")), "Scan", "1");
        } else {
            boolean error = true;
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> formItems = null;
            try {
                formItems = upload.parseRequest(req);
            } catch (FileUploadException ex) {
                ex.printStackTrace();
            }
            if (formItems != null) {
                formItems = formItems.stream().filter(item -> !item.isFormField() && "image/jpeg".equalsIgnoreCase(item.getContentType())).collect(Collectors.toList());
                if (formItems.size() == 1
                        && formItems.get(0).getName().endsWith(".jpg")) {
                    error = false;
                }
            }
            if (error) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_scan")), "Scan", "1");
            }
            return formItems.get(0);
        }
    }

    public void isDownloadingFileValid(String rootPath, String scanId) {
        if (!new File(rootPath + UPLOAD_DIRECTORY + File.separator + scanId + ".jpg").exists()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("scan_not_found")), "Scan", "1");
        }
    }
}
