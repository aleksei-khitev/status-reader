package ru.akhitev.status.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.akhitev.status.reader.messurement.service.MessureService;
import ru.akhitev.status.reader.reporter.EmailReportTemplate;
import ru.akhitev.status.reader.reporter.ReportSender;
import ru.akhitev.status.reader.reporter.ReportMaker;

import java.util.List;
import java.util.Set;

@Component
@Configuration
@ComponentScan(basePackageClasses = { Launcher.class})
@PropertySource("file:conf/email.properties")
public class Launcher {
    @Autowired
    private Set<MessureService> messureServices;

    @Autowired
    private ReportSender reportSender;

    private void process() {
        messureServices.forEach(MessureService::prepareValue);
        reportSender.sendMail();
    }

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Launcher.class);
        Launcher launcher = context.getBean("launcher", Launcher.class);
        launcher.process();

    }
}
