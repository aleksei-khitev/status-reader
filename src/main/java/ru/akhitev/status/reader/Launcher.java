package ru.akhitev.status.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;
import ru.akhitev.status.reader.messurement.service.MessureService;
import ru.akhitev.status.reader.reporter.ReportSender;

import java.util.Formatter;
import java.util.Set;

public class Launcher {
    private Set<MessureService> messureServices;

    private ReportSender reportSender;

    private void process() {
        messureServices.forEach(MessureService::prepareValue);
        reportSender.sendMailIfNeeded();
    }

}
