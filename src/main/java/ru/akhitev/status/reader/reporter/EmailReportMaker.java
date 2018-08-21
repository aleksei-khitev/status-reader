package ru.akhitev.status.reader.reporter;

import com.google.common.base.Joiner;
import org.springframework.stereotype.Service;
import ru.akhitev.status.reader.messurement.vo.Status;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class EmailReportMaker implements ReportMaker {
    private static final String REPORT_DATE_PATTERN = "dd.MM.yyyy - HH:mm";

    /** Список статусов работы исполнителей. */
    private List<Status> statuses;

    public EmailReportMaker() {
        statuses = new ArrayList<>();
    }

    /** {@inheritDoc}. */
    @Override
    public final void addStatus(final Status status) {
        statuses.add(status);
    }

    /** {@inheritDoc}. */
    public final String getBody(final EmailReportTemplate emailReportTemplate) {
        final String template = emailReportTemplate.template();
        final Joiner joiner = Joiner.on("");
        if (template != null) {
            return template
                    .replace("$statuses$", joiner.join(statuses))
                    .replace("$date$", getTime())
                    .replace("$host$", readHostName());
        } else {
            return "";
        }
    }

    @Override
    public boolean isNoExceedance() {
        AtomicBoolean isAnyExceedance = new AtomicBoolean(false);
        statuses.forEach(status -> {
            if (!status.isExceedance()) {
                isAnyExceedance.set(true);
            }
        });
        return isAnyExceedance.get();
    }

    private String getTime() {
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(REPORT_DATE_PATTERN);
        return LocalDateTime.now().format(formatter);
    }

    private String readHostName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            return "Couldn't define";
        }

    }
}
