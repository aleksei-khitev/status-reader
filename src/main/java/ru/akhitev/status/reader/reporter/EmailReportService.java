package ru.akhitev.status.reader.reporter;

import com.google.common.base.Joiner;
import ru.akhitev.status.reader.messurement.vo.Status;

import java.util.ArrayList;
import java.util.List;

public class EmailReportService implements ReportService {
    /** Список статусов работы исполнителей. */
    private List<Status> statuses;

    public EmailReportService() {
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
    private String getTime() {
        return "";
    }

    private String readHostName() {
        return "";
    }
}
