package ru.akhitev.status.reader.messurement.vo;

public class Status {
    private final String measurement;
    private final String value;

    public Status(String measurement, String value) {
        this.measurement = measurement;
        this.value = value;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Measurement: %s; Value: %s", measurement, value);
    }
}
