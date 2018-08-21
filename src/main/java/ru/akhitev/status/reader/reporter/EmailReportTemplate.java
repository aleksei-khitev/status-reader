package ru.akhitev.status.reader.reporter;

import com.google.common.base.Joiner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public enum EmailReportTemplate {
    /** Расширенный. */
    OK ("ok_mail_report.html", "ok_recepients"),
    /** Отчет об ошибке. */
    ERROR ("problem_mail_report.html", "problem_recepients");

    private String reportHtmlName;

    private String recepientListName;

    EmailReportTemplate(String reportHtmlName, String recepientListName) {
        this.reportHtmlName = reportHtmlName;
        this.recepientListName = recepientListName;
    }

    public String template() {
        try {
            String reportTemplateFolder = "conf/templates";
            return readFile(Paths.get(reportTemplateFolder, reportHtmlName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String recepientListName() {
        return recepientListName;
    }

    /**
     * Чтение файла.
     * @param path Путь.
     * @return Текст файла.
     * @throws IOException Исключение.
     */
    private String readFile(final Path path) throws IOException {
        final List<String> lines = Files.readAllLines(path);
        final Joiner joiner = Joiner.on("");
        return joiner.join(lines);
    }
}