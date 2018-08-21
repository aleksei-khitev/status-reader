package ru.akhitev.status.reader.reporter;

import com.google.common.base.Splitter;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchProviderException;

public class EmailReportSender implements ReportSender {

    @Override
    public void sendMail(ReportService reportService, EmailReportTemplate emailReportTemplate) {
        final Properties props = new Properties();
        props.setProperty("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        props.setProperty("mail.smtp.port", env.getProperty("mail.smtp.port"));
        props.setProperty("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class"));
        props.setProperty("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        props.setProperty("mail.host", env.getProperty("mail.host"));
        final Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        env.getProperty("mail.user"), env.getProperty("mail.password"));
            }
        });
        try {
            final Transport transport = mailSession.getTransport();
            final MimeMessage message = new MimeMessage(mailSession);
            message.setContent(reportService.getBody(type), "text/html;charset=utf-8");
            final Splitter splitter = Splitter.on(",");
            String recipientsField;
            switch (type) {
                case EXTENDED:
                    recipientsField = "ext_recepients";
                    message.setSubject(EXTENDED_REPORT_SUBJECT);
                    break;
                case SUCCESS:
                case ERROR:
                default:
                    recipientsField = "recepients";
                    message.setSubject(REPORT_SUBJECT);
                    break;
            }
            splitter.split(env.getProperty(recipientsField)).forEach(recepient -> {
                try {
                    message.addRecipients(Message.RecipientType.TO, recepient);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            message.setFrom(new InternetAddress(env.getProperty("mail.user")));
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
