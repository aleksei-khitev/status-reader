package ru.akhitev.status.reader.reporter;

public interface ReportSender {
    void sendMail(final ReportService reportService, final EmailReportTemplate emailReportTemplate);
}
