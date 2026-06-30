package com.archiving.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyyMMdd");

    private DateUtil() {}

    public static String todayYmd() {
        return LocalDate.now().format(YMD);
    }

    public static String getDate(int dayOffset) {
        return LocalDate.now().plusDays(dayOffset).format(YMD);
    }
}
