package org.example;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractBeanField<LocalDate> {
    private final DateTimeFormatter dateFormatter;

    public LocalDateConverter() {
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Override
    protected Object convert(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return LocalDate.parse(value, dateFormatter);
    }
}