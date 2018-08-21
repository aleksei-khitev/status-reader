package ru.akhitev.status.reader.reporter;

import com.google.common.base.Joiner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public enum EmailReportTemplate {
    /** Расширенный. */
    OK ("ok_mail_report.html"),
    /** Отчет об ошибке. */
    ERROR ("problem_mail_report.html");

    private String reportHtmlName;

    EmailReportTemplate(String reportHtmlName) {
        this.reportHtmlName = reportHtmlName;
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