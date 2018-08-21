package ru.akhitev.status.reader.messurement.vo;

public class Status {
    private final String measurement;
    private final String value;
    private final boolean exceedance;

    public Status(String measurement, String value, boolean exceedance) {
        this.measurement = measurement;
        this.value = value;
        this.exceedance = exceedance;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getValue() {
        return value;
    }

    public boolean isExceedance() {
        return exceedance;
    }

    @Override
    public String toString() {
        return String.format("Measurement: %s; Value: %s", measurement, value);
    }
}
