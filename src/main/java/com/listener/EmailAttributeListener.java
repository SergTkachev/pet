package com.listener;

import com.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.*;

public class EmailAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        if ("admitted_users".equals(attributeName)) {
            final List<NavigableMap<String, NavigableSet<User>>> admittedUsers = (List<NavigableMap<String, NavigableSet<User>>>) event.getValue();
            event.getSession().removeAttribute("admitted_users");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendEmails(admittedUsers.get(0), admittedUsers.get(1));
                }
            }).start();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }

    private Session createMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 465);
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "axxyr.reminder@gmail.com",
                                "axxyr2016");
                    }
                });
    }

    private void sendEmail(Session mailSession, String email, String content) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("noreply@mail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("University inspection board");
            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart.setContent(content, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            message.saveChanges();
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendEmails(Session mailSession, String form, NavigableMap<String, NavigableSet<User>> map) {
        String contentTemplate = "<html>" +
                "<body>" +
                "<h1>Congratulations!!!</h1>" +
                "<br>" +
                "<br>"
                + "You have successfully passed your exams and can be admitted for learning in our university!" +
                "<br>" +
                "<br>" +
                "<b>Faculty:</b> %faculty%" +
                "<br>" +
                "<b>Form:</b> %form%";
        for (Map.Entry entry : map.entrySet()) {
            String faculty = (String) entry.getKey();
            NavigableSet<User> users = (NavigableSet<User>) entry.getValue();
            for (User user : users) {
                sendEmail(mailSession,
                        user.getEmail(),
                        contentTemplate
                                .replaceFirst("%faculty%", faculty)
                                .replaceFirst("%form%", form));
            }
        }
    }

    private void sendEmails(NavigableMap<String, NavigableSet<User>> budgetMap,
                            NavigableMap<String, NavigableSet<User>> contractMap) {
        Session mailSession = createMailSession();
        sendEmails(mailSession, "Budget", budgetMap);
        sendEmails(mailSession, "Contract", contractMap);
    }
}
