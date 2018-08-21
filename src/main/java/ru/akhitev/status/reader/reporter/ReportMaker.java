package ru.akhitev.status.reader.reporter;

import ru.akhitev.status.reader.messurement.vo.Status;

public interface ReportMaker {
    /**
     * Добавляет статус в тело отчета.
     * @param status Статус.
     */
    void addStatus(final Status status);

    /**
     * Возвращает сформированное тело отчета.
     * @param type Тип отчета (успешний, не успешный, расширенный)
     * @return Тело отчета.
     */
    String getBody(EmailReportTemplate type);

    boolean isAnyExceedance();
}
