package ru.akhitev.status.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.akhitev.status.reader.messurement.service.MessureService;
import ru.akhitev.status.reader.reporter.EmailReportTemplate;
import ru.akhitev.status.reader.reporter.ReportSender;
import ru.akhitev.status.reader.reporter.ReportMaker;

import java.util.Set;

public class Launcher {
    private Set<MessureService> messureServices;

    @Autowired
    private ReportMaker reportMaker;

    @Autowired
    private ReportSender reportSender;

    private void process() {
        messureServices.forEach(MessureService::prepareValue);
        reportSender.sendMail();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Launcher launcher = context.getBean("launcher", Launcher.class);
        launcher.process();

    }

    public void setMessureServices(Set<MessureService> messureServices) {
        this.messureServices = messureServices;
    }

    public void setReportMaker(ReportMaker reportMaker) {
        this.reportMaker = reportMaker;
    }
}
