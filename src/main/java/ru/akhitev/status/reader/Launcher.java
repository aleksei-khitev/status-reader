package ru.akhitev.status.reader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.akhitev.status.reader.messurement.service.MessureService;
import ru.akhitev.status.reader.reporter.EmailReportTemplate;
import ru.akhitev.status.reader.reporter.ReportService;

import java.util.Set;

public class Launcher {
    private Set<MessureService> messureServices;
    private ReportService reportService;

    private void process() {
        for (MessureService messureService : messureServices) {
            reportService.addStatus(messureService.prepareValue());
        }
        String report = reportService.getBody(EmailReportTemplate.OK);
        System.out.println(report);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Launcher launcher = context.getBean("launcher", Launcher.class);
        launcher.process();
    }

    public void setMessureServices(Set<MessureService> messureServices) {
        this.messureServices = messureServices;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
