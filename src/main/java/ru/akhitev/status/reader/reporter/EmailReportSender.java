package ru.akhitev.status.reader.reporter;

import com.google.common.base.Splitter;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailReportSender implements ReportSender {
    @Autowired
    private ReportMaker reportMaker;

    @Autowired
    private Environment env;

    @Override
    public void sendMail() {
        Properties props = prepareSenderProperties();
        final Session mailSession = Session.getInstance(props, null);
        EmailReportTemplate emailReportTemplate = defineEmailReportTemplate();
        try {
            final Transport transport = mailSession.getTransport();
            final MimeMessage message = new MimeMessage(mailSession);
            message.setContent(reportMaker.getBody(emailReportTemplate), "text/html;charset=utf-8");
            final Splitter splitter = Splitter.on(",");
            splitter.split(env.getProperty(emailReportTemplate.recepientListName())).forEach(recepient -> {
                try {
                    message.addRecipients(Message.RecipientType.TO, recepient);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            message.setSubject("Server Monitoring");
            message.setFrom(new InternetAddress(env.getProperty("mail.user")));
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Properties prepareSenderProperties() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        props.setProperty("mail.smtp.port", env.getProperty("mail.smtp.port"));
        props.setProperty("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class"));
        props.setProperty("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        props.setProperty("mail.host", env.getProperty("mail.host"));
        return props;
    }

    private EmailReportTemplate defineEmailReportTemplate() {
        if (reportMaker.isAnyExceedance()) {
            return EmailReportTemplate.ERROR;
        } else {
            return EmailReportTemplate.OK;
        }
    }
}
