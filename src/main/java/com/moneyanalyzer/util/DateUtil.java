package com.moneyanalyzer.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    private static final DateTimeFormatter PHONEPE =
            DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public static LocalDate parsePhonePeDate(String s) {
        return LocalDate.parse(s, PHONEPE);
    }
}
